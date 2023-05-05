theme: /OtherQuestions
    
    state: Complaints
        intent!: /Complaints
        script: 
            answer("a13.000.012");
            $dialer.hangUp()
        
    state: AskForManager
        intent!: /AskForManager
        script: 
            answer("a13.000.013");
            $dialer.hangUp()
        
    state: TransferToOperator
        intent!: /TransferToOperator
        script: 
            answer("a13.000.014");
            $dialer.hangUp()
