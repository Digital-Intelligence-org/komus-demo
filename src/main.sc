require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: topics/customerPickup.sc
require: topics/other.sc

init:
    bind("preProcess", function($context) {
        $.session.dialog = $.session.dialog || [];
        if ($.request.query || ($.session.dialog.length
            && $.session.dialog[$.session.dialog.length - 1].indexOf("КЛИЕНТ") === -1)) {
            $.session.dialog.push("КЛИЕНТ: " + ($.request.query || "<не распознано>") + "\n");
        }
        if ($.currentState.indexOf("/Repeat") === -1) $.session.repeatArray = [];
        if ($.currentState.indexOf("/Objection") !== -1) {
            $.session.objectionCount = $.session.objectionCount ? $.session.objectionCount + 1 : 1;
        }
        # if ($.session.objectionCount === 3) { $.temp.targetState = "/ObjectionProcessed"; }
    });
    
    bind("postProcess", function($context) {
        $.session.lastState = $context.currentState;
        # $dialer.setNoInputTimeout($.session.setNoInputTimeout || $.injector.setNoInputTimeout);
        # delete $.session.setNoInputTimeout;
    });

theme: /

    state: Start
        q!: $regex</start>
        a: Тишина 3 сек
        

    state: Hello
        intent!: /Hello
        a: a13.000.001
        
    state: Repeat
        intent!: /Repeat
        go!: {{$session.lastState}}

    state: NoMatch || noContext=true
        event!: noMatch
        event!: speechNotRecognized
        script:
            $session.NoMatchCounter = ++1 || 1;
            switch ($session.NoMatchCounter) {
                case 1:
                    $reactions.answer("a13.000.015");
                case 2:
                    $reactions.answer("a13.000.016");
                case 3:
                    $reactions.answer("a13.000.017");
                    $reactions.transition("./");
                }
            

    # state: Match
    #     event!: match
    #     a: {{$context.intent.answer}}