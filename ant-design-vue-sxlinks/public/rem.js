(function (doc, win) {
    var docEl = doc.documentElement,
    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
    recalc = function () {
      var clientWidth = docEl.clientWidth
      if (!clientWidth) return;
        docEl.style.fontSize = 100 * (clientWidth / 1920) + 'px'
        // docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
      /*
       * 100 -> html,body {  font-size:100px; }
       * 750 -> 此处以 iPhone6 两倍设计稿 宽度750px 布局页面
       * 根据具体情况改变这两个数值
       */
    };
    recalc()
  if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false)
    doc.addEventListener('DOMContentLoaded', recalc, false)
})(document, window)