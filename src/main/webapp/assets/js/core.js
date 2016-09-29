/**
 * MCD-WEB前端核心js 与具体的业务模版没有关系的才可以写到这里
 * 
 */



/**
 * 输入框字数变化提示
 * @param textArea输入框
 * @param numItem 最大字数
 */
function textAreaInputNumTip(textArea,numItem) {
    var max = numItem.text(),curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        var _value = $(this).val().replace(/\n/gi,"");
        numItem.text(max - _value.length);
    });
}

