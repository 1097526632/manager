export function validateNum(rule, value, callback) {
  if (value === '') {
    callback(new Error('请输入数字'));
  } else {
      try {
        parseFloat(value)
        callback();
      }catch (e) {
        callback(new Error('请输入数字'));
      }
  }
}
