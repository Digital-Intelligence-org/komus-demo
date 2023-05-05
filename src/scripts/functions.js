function answer(answerId) {
    var url = $.injector.selectel + $answers[answerId].audio;
    var text = $answers[answerId].text;
    $.session.dialog.push("БОТ: " + tts + "\n");
    if (testMode()) {
        $reactions.answer(answerId);
    } else if ($.request.channelType === "chatwidget") {
        $reactions.answer(text);
    } 
}

function sendSms() {
    
}
