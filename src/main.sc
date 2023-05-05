require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: topics/customerPickup.sc
require: topics/other.sc

init:
    # bind("preProcess", function($context) {
    #     $.session.dialog = $.session.dialog || [];
    #     if ($.request.query || ($.session.dialog.length
    #         && $.session.dialog[$.session.dialog.length - 1].indexOf("КЛИЕНТ") === -1)) {
    #         $.session.dialog.push("КЛИЕНТ: " + ($.request.query || "<не распознано>") + "\n");
    #     }
    #     if ($.currentState.indexOf("/Repeat") === -1) $.session.repeatArray = [];
    #     if ($.currentState.indexOf("/Objection") !== -1) {
    #         $.session.objectionCount = $.session.objectionCount ? $.session.objectionCount + 1 : 1;
    #     }
    #     # if ($.session.objectionCount === 3) { $.temp.targetState = "/ObjectionProcessed"; }
    # });
    
    bind("postProcess", function($context) {
        $.session.lastState = $context.currentState;
        $dialer.setNoInputTimeout($.session.setNoInputTimeout || $.injector.setNoInputTimeout);
        delete $.session.setNoInputTimeout;
    });

theme: /

    state: Start
        q!: $regex</start>
        script:
            $session.phoneNumber = $dialer.getCaller();
            $.session.setNoInputTimeout = 3000;
        

    state: Hello
        intent!: /Hello
        script:
            answer("a13.000.001");
        
    state: Repeat
        intent!: /Repeat
        go!: {{$session.lastState}}

    state: NoMatch || noContext=true
        event!: noMatch
        script:
            $session.NoMatchCounter = ++1 || 1;
            switch ($session.NoMatchCounter) {
                case 1:
                    answer("a13.000.015");
                case 2:
                    answer("a13.000.016");
                case 3:
                    answer("a13.000.017");
                    botHangup();
                }
    
    state: SpeechNotRecognized || noContext=true
        event!: speechNotRecognized
        if: $session.lastState === "/SpeechNotRecognized"
            script:
                botHangup();
        else:
            go!: {{$session.lastState}}
            
    state: Hangup
        event!: botHangup
        script:
            $jsapi.stopSession();
