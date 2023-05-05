theme: /CustomerPickup
    
    state: PickupGeneral
        intent!: /PickupGeneral
        script: 
            answer("a13.000.005");
        
        state: /PickupConditions
            intent!: /PickupConditions
            script:
                answer("a13.000.003");
            
            state: Agree
                intent: /Yes
                script:
                    sendSMS($session.phoneNumber, "Адреса пунктов самовывоза компании Комус: https://www.komus.ru/store/carrier-list/?code=Boxberry");
                    answer("a13.000.004");
                go!: ../AskForQuestions
                
            state: Disagree
                intent: /No
                go!: ../AskForQuestions
                
        state: StorageTime
            intent!: /StorageTime
            script:
                answer("a13.000.018");
            go!: ./AskForQuestions
            
        state: PriceOfOrderForPickup
            intent!: /PriceOfOrderForPickup
            script:
                answer("a13.000.019");
            go!: ./AskForQuestions
            
        state: AskForQuestions || noContext=true
            script:
                $session.questionsPrompter = ++1 || 1;
                switch ($session.questionsPrompter) {
                    case 1:
                        answer("a13.000.006");
                    case 2:
                        answer("a13.000.007");
                    case 3:
                        answer("a13.000.008");
                        $session.questionsPrompter = 0;
                    }
            
        state: PickupOther
            intent: /PickupOther
            script:
                answer("a13.000.011");
                