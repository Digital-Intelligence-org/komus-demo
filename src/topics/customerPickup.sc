theme: /CustomerPickup
    
    state: PickupGeneral
        intent!: /PickupGeneral
        a: a13.000.005
        
        state: /PickupConditions
            intent!: /PickupConditions
            a: a13.000.003
            
            state: Agree
                intent: /Yes
                script:
                    sendSMS(телефон, "Адреса пунктов самовывоза компании Комус: https://www.komus.ru/store/carrier-list/?code=Boxberry");
                a: a13.000.004
                go!: ../AskForQuestions
                
            state: Disagree
                intent: /No
                go!: ../AskForQuestions
                
        state: /StorageTime
            intent!: /StorageTime
            a: a13.000.018
            go!: ./AskForQuestions
            
        state: /PriceOfOrderForPickup
            intent!: /PriceOfOrderForPickup
            a: a13.000.019
            go!: ./AskForQuestions
            
        state: AskForQuestions
            script:
                $session.questionsPrompter = ++1 || 1;
                switch ($session.questionsPrompter) {
                    case 1:
                        $reactions.answer("a13.000.006");
                    case 2:
                        $reactions.answer("a13.000.007");
                    default:
                        $reactions.answer("a13.000.008");
                    }
            
        state: /PickupOther
            event: NoMatch
            a: a13.000.011