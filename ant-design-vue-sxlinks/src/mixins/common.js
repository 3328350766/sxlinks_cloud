import Cookies from 'js-cookie'
export default {
  data () {
    return {
    }
  },
  computed: {
    // 网页高度
    bodyWidth () {
      return document.body.clientWidth
    },
    // 网页宽度
    bodyHeight () {
      return document.body.clientHeight
    },
  },
  created () {
  },
  mounted () {
  },
  destroyed () {
  },
  methods: {
    setCookies (key, val, option) {
      if (option == null) {
        option = { expires: 15 }
      }
      Cookies.set(key, val, option)
    },
    goBack () {
      this.$router.go(-1)
    },
    refresh () {
      this.$router.go(0)
    },
    parseString (object) {
      if (typeof object === 'undefined' || object == null) {
        return ''
      }
      if (typeof object === 'number') {
        return object.toString()
      }
      if (typeof object === 'boolean') {
        return object.toString()
      }
      if (typeof object === 'object') {
        return JSON.stringify(object)
      }
      return ''
    },
    isBlank (val) {
      if (typeof val === 'undefined') {
        return true
      }
      if (val == null || val === '') {
        return true
      }
      return false
    },
    // 封装定制删除数组中的值
    contains (a, obj) {
      let i = a.length
      while (i--) {
        if (a[i] === obj) {
          return i
        }
      }
      return false
    },
    //获取url后边参数
    getUrlKey: function (name) {
      return (
        decodeURIComponent(
          (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ''])[1].replace(
            /\+/g,
            '%20'
          )
        ) || null
      )
    },
    /**
     *
     */
    resetForm (data) {
      let formKeys = Object.keys(data)
      for (let k of formKeys) {
        data[k] = null
      }
    },
    sortArray (propertyName) {
      return function (object1, object2) {
        let value1 = object1[propertyName];
        let value2 = object2[propertyName];

        if (value1 < value2) {
          return -1;
        } else if (value1 > value2) {
          return 1;
        } else {
          return 0;
        }
      }
    },
    // 获取对象类型
    getObjectType (obj) {
      let toString = Object.prototype.toString
      let map = {
        '[object Boolean]': 'boolean',
        '[object Number]': 'number',
        '[object String]': 'string',
        '[object Function]': 'function',
        '[object Array]': 'array',
        '[object Date]': 'date',
        '[object RegExp]': 'regExp',
        '[object Undefined]': 'undefined',
        '[object Null]': 'null',
        '[object Object]': 'object',
      }
      if (obj instanceof Element) {
        return 'element'
      }
      return map[toString.call(obj)]
    },
    isNumber (obj) {
      return this.getObjectType(obj) == 'number'
    },
    isString (obj) {
      return this.getObjectType(obj) == 'string'
    },
    isArray (obj) {
      return this.getObjectType(obj) == 'array'
    },
    hasOwn (obj, key) {
      return Object.prototype.hasOwnProperty.call(obj, key)
    },

    isNotBlank (val) {
      return !this.isBlank(val)
    },
    isBlank (val) {
      if (this.isNull(val)) {
        return true
      }
      if (typeof val === 'string') {
        return val.trim() == ''
      }
      if (typeof val === 'object') {
        for (let key in val) {
          return false
        }
        return true
      }
      return false
    },
    isNotNull (val) {
      return !this.isNull(val)
    },
    isNull (val) {
      // 特殊判断
      if (val && parseInt(val) === 0) return false
      const list = ['$parent']
      if (val instanceof Date || typeof val === 'boolean' || typeof val === 'number') return false
      if (val instanceof Array) {
        if (val.length === 0) return true
      } else if (val instanceof Object) {
        val = this.deepClone(val)
        list.forEach((ele) => {
          delete val[ele]
        })
        for (let o in val) {
          return false
        }
        return true
      } else {
        if (val === 'null' || val == null || val === 'undefined' || val === undefined || val === '') {
          return true
        }
        return false
      }
      return false
    },

    // 对象深拷贝
    deepClone (data) {
      let type = this.getObjectType(data)
      let obj
      if (type === 'array') {
        obj = []
      } else if (type === 'object') {
        obj = {}
      } else {
        // 不再具有下一层次
        return data
      }
      if (type === 'array') {
        for (let i = 0, len = data.length; i < len; i++) {
          data[i] = (() => {
            if (data[i] === 0) {
              return data[i]
            }
            return data[i]
          })()
          if (data[i]) {
            delete data[i].$parent
          }
          obj.push(this.deepClone(data[i]))
        }
      } else if (type === 'object') {
        for (let key in data) {
          if (data) {
            delete data.$parent
          }
          obj[key] = this.deepClone(data[key])
        }
      }
      return obj
    },

    // 合并json
    mergeObject () {
      let target = arguments[0] || {}
      let deep = false
      let arr = Array.prototype.slice.call(arguments)
      let i = 1
      let options, src, key, copy
      let isArray = false
      if (typeof target === 'boolean') {
        deep = target
        i++
        target = arguments[1]
      }
      for (; i < arr.length; i++) {
        // 循环传入的对象数组
        if ((options = arr[i]) != null) {
          // 如果当前值不是null，如果是null不做处理
          for (key in options) {
            // for in循环对象中key
            copy = options[key]
            src = target[key]
            // 如果对象中value值任然是一个引用类型
            if (deep && (toString.call(copy) === '[object Object]' || (isArray = toString.call(copy) == '[object Array]'))) {
              if (isArray) {
                // 如果引用类型是数组
                // 如果目标对象target存在当前key，且数据类型是数组，那就还原此值，如果不是就定义成一个空数组;
                src = toString.call(src) === '[object Array]' ? src : []
              } else {
                // 如果目标对象target存在当前key，且数据类型是对象，那就还原此值，如果不是就定义成一个空对象;
                src = toString.call(src) === '[object Object]' ? src : {}
              }
              // 引用类型就再次调用extend，递归，直到此时copy是一个基本类型的值。
              target[key] = this.mergeObject(deep, src, copy)
            } else if (copy !== undefined && copy !== src) {
              // 如果这个值是基本值类型，且不是undefined
              target[key] = copy
            }
          }
        }
      }
      return target
    },

    // 获取dom在屏幕中的top和left
    getDomTopLeftById (id) {
      let dom = document.getElementById(id)
      let top = 0
      let left = 0
      if (dom != null) {
        top = dom.getBoundingClientRect().top
        left = dom.getBoundingClientRect().left
      }
      return { top: top, left: left }
    },
    objToOne (obj) {
      console.log(obj)
      let tmpData = {}
      for (let index in obj) {
        if (typeof obj[index] == 'object' && !this.isArrayFn(obj[index])) {
          let resObj = this.objToOne(obj[index])
          Object.assign(tmpData, resObj) // 这里使用对象合并
        } else {
          tmpData[index] = obj[index]
        }
      }
      return tmpData
    },
    isArrayFn(value) {
      if (typeof Array.isArray === "function") {
        return Array.isArray(value);
      } else {
        return Object.prototype.toString.call(value) === "[object Array]";
      }
    },
    urlEncode (val) {
      return encodeURIComponent(val)
    },
    urlDecode (val) {
      return decodeURIComponent(val)
    },
    urlEncodeObject (obj, ingoreFields) {
      if (toString.call(obj) != '[object Object]') {
        return obj
      }
      let result = {}
      for (let key in obj) {
        if (this.isBlank(obj[key])) {
          continue
        }
        if (ingoreFields != null && ingoreFields.indexOf(key) >= 0) {
          result[key] = obj[key]
        } else {
          result[key] = this.urlEncode(obj[key])
        }
      }
      return result
    },
    // 根据数据字典，查询指定字典dict指定值code的dictItem.text
    getDictLabelByCode (dict, code) {
      // let dictItem = this.getDictItemByCode(dict, code)
      // if (dictItem != null) {
      //   return dictItem['text']
      // } else {
      //   return ''
      // }
    },
    // 根据数据字典，查询指定字典dict指定值code的dictItem.extend
    getDictExtendByCode (dict, code) {
      // let dictItem = this.getDictItemByCode(dict, code)
      // if (dictItem == null) {
      //   return null
      // }
      // let extend = dictItem['extend']
      // if (extend == null || extend.trim() == 'null') {
      //   return null
      // }
      // return dictItem['extend']
    },
    getSettingByName(settingName) {
      let gaeaSetting = JSON.parse(localStorage.getItem('AJReportDict'))
      if (gaeaSetting[settingName] != null) {
        return gaeaSetting[settingName]
      } else {
        // console.error('没有找到系统参数' + settingName + '，请与后端联系')
        return null
      }
    },
  }
}
