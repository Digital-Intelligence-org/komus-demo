require: topics/customerPickup.sc
require: topics/other.sc
require: scripts/functions.js

require: dicts/answers.yaml
  var = $answers
  
require: patterns.sc
  module = sys.zb-common

init:
    
    $global.$ = {
        __noSuchProperty__: function(property) {
            return $jsapi.context()[property];
        }
    };
    
    bind("preProcess", function($context) {
        if ($.session.lastState === "/Start" && $.currentState !== "/Hello") {
            answer("a13.000.002")}
    });
    
    bind("postProcess", function($context) {
        if ($context.currentState !== "/CustomerPickup/PickupGeneral/AskForQuestions") {
            $.session.lastState = $.currentState;
            }
        $dialer.setNoInputTimeout($.session.setNoInputTimeout || $.injector.setNoInputTimeout);
        delete $.session.setNoInputTimeout;
    });

theme: /

    state: Start
        q!: $regex</start>
        script:
            $session.phoneNumber = $dialer.getCaller();
            $session.setNoInputTimeout = 3000;
        

    state: Hello
        # intent!: /Hello
        q!: Hello
        script:
            answer("a13.000.001");
        
    state: Repeat || noContext=true
        # intent!: /Repeat
        q!: Repeat
        go!: {{$session.lastState}}

    state: NoMatch || noContext=true
        event!: noMatch
        script:
            $session.NoMatchCounter = ++$session.NoMatchCounter || 1;;
            switch ($session.NoMatchCounter) {
                case 1:
                    answer("a13.000.015");
                    break;
                case 2:
                    answer("a13.000.016");
                    break;
                default:
                    answer("a13.000.017");
                    $reactions.transition("/Hangup");;
                }
    
    state: SpeechNotRecognized || noContext=true
        event!: speechNotRecognized
        if: $session.lastState === "/SpeechNotRecognized"
            go!: /Hangup
        go!: {{$session.lastState}}
            
    state: Hangup
        event!: hangup
        event!: botHangup
        script:
            $dialer.hangUp()
            if ($.request.channelType === "chatwidget") {
                $reactions.answer("Закрыл сессию");
                }
            $jsapi.stopSession();
