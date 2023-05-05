function answer(answerId) {
    var url = $.injector.selectel + $answers[answerId];
    var tts = $answers[answerId].tts;
    $.session.dialog.push("БОТ: " + tts + "\n");
    if (testMode()) {
        $reactions.answer(answerId);
    } else if ($.request.channelType === "chatwidget") {
        $reactions.answer(tts + " " + $.session.timezoneMode);
    } else if (bargeIn === "ignore") {
        $reactions.audio({"value": url, "ignoreBargeIn": true});
    } else {
        $reactions.audio({"value": url, "bargeInIf": bargeIn});
    }
    $.session.repeatArray.push({"answerId": answerId, "bargeIn": bargeIn});
}

function sendSms() {
    
}
