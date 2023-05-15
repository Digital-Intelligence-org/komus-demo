theme: /CustomerPickup
    
    state: PickupGeneral
        intent!: /PickupGeneral
        # q!: PickupGeneral
        script: 
            if ($session.lastState == "/CustomerPickup/PickupGeneral" && $session.lastStateRepeated !== "/CustomerPickup/PickupGeneral") {
                answer("a13.000.011");
                $reactions.transition("/Hangup");
            } else {
                answer("a13.000.005");
            }
        
        state: PickupConditions
            intent!: /PickupConditions
            intentGroup!: /PickupConditions
            # q!: PickupConditions
            script:
                answer("a13.000.003");
            
            state: Agree
                intent: /Yes
                # q: Yes
                script:
                    var reply = sendSMS($session.phoneNumber, "Адреса пунктов самовывоза компании Комус: https://www.komus.ru/store/carrier-list/?code=Boxberry");
                    $.response.replies = $.response.replies || [];
                    $.response.replies.push(reply);
                    answer("a13.000.004");
                go!: /CustomerPickup/PickupGeneral/AskForQuestions
                
            state: Disagree
                intent: /No
                # q: No
                go!: /CustomerPickup/PickupGeneral/AskForQuestions
                
        state: StorageTime
            intent!: /StorageTime
            # q!: StorageTime
            script:
                answer("a13.000.018");
            go!: ../AskForQuestions
            
        state: PriceOfOrderForPickup
            intent!: /PriceOfOrderForPickup
            # q!: PriceOfOrderForPickup
            script:
                answer("a13.000.019");
            go!: ../AskForQuestions
            
        state: AskForQuestions 
            script:
                $session.questionsPrompter = ++$session.questionsPrompter || 1;
                switch ($session.questionsPrompter) {
                    case 1:
                        answer("a13.000.006");
                        break;
                    case 2:
                        answer("a13.000.007");
                        break;
                    default:
                        answer("a13.000.008");
                        $session.questionsPrompter = 0;
                        break;
                    }
                
            state: Yes
                intent: /Yes
                # q: Yes
                script:
                    answer("a13.000.009");
                
            state: No
                intent: /No
                # q: No
                script:
                    answer("a13.000.010");
                go!: /Hangup
        