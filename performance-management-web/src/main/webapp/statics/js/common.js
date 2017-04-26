 /*
   * 转换成时间戳
   * @param: [String || Date]{date}
  */
  function toTimestamp(date) {
    date = new Date(date);
    let UCT = Date.parse(date);
    return UCT
  },
  /*
   * 转换成时间字符
   * @param: [String || Date]{UCT}
   * @param: [String]{format}
   * @param: [Boolean]{symbol}
  */
function toDateString(UCT,format,symbol) {
    if (typeof UCT == 'string') {
      UCT = this.toTimestamp(UCT);
    }
    let date = new Date(UCT);
    let y = date.getFullYear();
    let m = date.getMonth()+1;
    let d = date.getDate();
    let h = date.getHours();
    let mm = date.getMinutes();
    let s = date.getSeconds();
    
    date = [y,digit(m),digit(d)];
    let time = [digit(h),digit(mm),digit(s)];

    if (symbol === 1) {
      format = format || 'hh:mm:ss';
      return parse([],time,format)
    }else if(symbol === 0){
      format = format || 'YYYY-MM-DD';
      return parse(date,[],format)
    }
    return parse(date,time,format)
  }
};
