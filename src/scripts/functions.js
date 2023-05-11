function answer(answerId) {
    var url = $.injector.selectel + $answers[answerId].audio;
    var text = $answers[answerId].text;
    if (testMode()) {
        $reactions.answer(answerId);
    } else if ($.request.channelType === "chatwidget") {
        $reactions.answer(text);
    } 
}

function sendSMS(phoneNumber, text) {
    var reply = {
      "type": "sms",
      "text": text,
      "destination": phoneNumber
    };
    $.response.replies = $.response.replies || [];
    $.response.replies.push(reply);
}
