<template>
  <div id="userLayout" :class="['user-layout-wrapper', device]" class="container">
    <div class="signs">
      <div class="signLeft">
        <div class="top">
          <div class="header">
            <a href="/">
<!--              <img src="~@/assets/logo.png" class="logo" alt="logo">-->
              <span class="title">四象物联云平台</span>
            </a>
          </div>
          <div class="desc">
            国内专业的物联网通用接入平台
          </div>
        </div>

        <route-view></route-view>

        <div class="footer">
          <div class="links">
<!--            <a href="http://doc.sxlinks.com" target="_blank">帮助</a>-->
          </div>
          <div class="copyright">
            Copyright &copy; 2021-2024 <a href="http://www.sxlinks.com" target="_blank">四象物联</a> 出品
          </div>
        </div>
    </div>
    <div class="signRight"></div>
    </div>
  </div>
</template>

<script>
  import RouteView from "@/components/layouts/RouteView"
  import { mixinDevice } from '@/utils/mixin.js'

  export default {
    name: "UserLayout",
    components: { RouteView },
    mixins: [mixinDevice],
    data () {
      return {}
    },
    mounted () {
      document.body.classList.add('userLayout')
    },
    beforeDestroy () {
      document.body.classList.remove('userLayout')
    },
  }
</script>

<style lang="less" scoped>
.container {
      width: 100%;
      min-height: 100%;
      background: #f0f2f5 url(~@/assets/login/bg.png) no-repeat 50%;
      background-size: cover;
      // padding: 110px 0 144px;
      position: relative;
       display: flex;
      justify-content: center;
      align-items: center;

      a {
        text-decoration: none;
      }

      .top {
        text-align: center;
        margin-top: 38px;
        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: #00bf8a;
            font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: #00bf8a;
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }

      .main {
        min-width: 260px;
        width: 368px;
        margin-left: 23%
      }

      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;

        .links {
          margin-bottom: 8px;
          font-size: 14px;
          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;
            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }
        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }
.signs{
  display: flex;
  justify-content: center;
  width: 90%;
  height: 620px;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0px 5px 50px 0px rgba(186, 186, 186, 0.5);
  .signLeft{
    flex: 1;
    .logins{
      width: 70px;
      height: 70px;
      margin-left: 23%;
      margin-top: 27px;
      img{
        width: 70px;
      height: 70px;
      }
    }
    .titke{
      font-size: 20px;
      color: #333333;
      margin-left: 23%;
      margin-top: 24px;
    margin-bottom: 10px;
    font-weight: bold;
    letter-spacing: 2px;
    }
    .name{
        font-size: 12px;
        color: #333333;
        margin-left: 23%;
        letter-spacing: 7px;
    }
  }
  .signRight{
    flex: 1;
    background: url(~@/assets/login/bg-right.png) no-repeat center center;
    background-size: 100% 100%;
    height: 100%;
  }
}
  #userLayout.user-layout-wrapper {
    height: 100%;

    &.mobile {
      .container {
        .main {
          max-width: 368px;
          width: 98%;
        }
      }
    }

    .container {
     width: 100%;
      min-height: 100%;
      background: #f0f2f5 url(~@/assets/login/bg.png) no-repeat 50%;
      background-size: cover;
      // padding: 110px 0 144px;
      position: relative;
       display: flex;
      justify-content: center;
      align-items: center;


      a {
        text-decoration: none;
      }

      .top {
        text-align: center;

        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }

      .main {
        min-width: 260px;
        width: 368px;
        margin: 0 auto;
      }

      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;

        .links {
          margin-bottom: 8px;
          font-size: 14px;
          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;
            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }
        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }
  }
</style>