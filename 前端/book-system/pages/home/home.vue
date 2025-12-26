<template>
  <view style="padding: 15px;">
    <!-- 个人信息 -->
    <view class="profile">
      <!-- 未登录 -->
      <button @click="wxLogin" v-if="!userInfo">微信快捷登录</button>
      <!-- 登录后 -->
      <view class="overview" v-else>
        <!-- 头像 -->
        <navigator url="/pages/home/profile" hover-class="none" open-type="navigate">
          <image class="avatar" :src="userInfo.avatar" mode="aspectFill"></image>
        </navigator>
        <!-- 昵称 -->
        <view class="meta">
          <view class="nickname">{{userInfo.nickname}}</view>
          <navigator class="extra" url="/pages/home/profile" hover-class="none" open-type="navigate">
            <text class="update">修改</text>
          </navigator>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
	import {login} from 'config/login.js';
	export default {
		data() {
			return {
				userInfo:{
					avatar:'',
					nickname:''
				}
				
			}
			
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('userInfo');
	
		},
		onShow() {
			this.userInfo = uni.getStorageSync('userInfo');
		},
		methods: {
			wxLogin(){
				uni.login({
					provider:'weixin',
					success:(loginRes)=>{
						const {code} = loginRes;
						if(code){
							uni.getUserInfo({
								desc:"获取用户的信息",
								success:(userInfoRes)=>{
									const userInfo= userInfoRes.userInfo;
									this.loginWithUserInfo(code,userInfo);
								}

							})
						}
					}
				})
			},
			loginWithUserInfo(code,userInfo){
				const params = {
					code,
					nickname: userInfo.nickName,
					avatar: userInfo.avatarUrl
				}
				login(params).then(res=>{
					this.userInfo = res.data.users
					uni.setStorageSync('userInfo',this.userInfo)
					uni.setStorageSync('token',res.data.token)
				})
			}
			
		}
	}
</script>

<style scoped lang="scss">
    /* 用户信息 */
    .profile {
        margin-top: 30rpx;
        position: relative;

        .overview {
            display: flex;
            height: 120rpx;
            padding: 0 36rpx;
            color: #000000;
        }

        .avatar {
            width: 120rpx;
            height: 120rpx;
            border-radius: 50%;
            background-color: #eee;
        }

        .gray {
            filter: grayscale(100%);
        }

        .meta {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            line-height: 30rpx;
            padding: 16rpx 0;
            margin-left: 20rpx;
        }

        .nickname {
            max-width: 180rpx;
            margin-bottom: 16rpx;
            font-size: 30rpx;

            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .extra {
            display: flex;
            font-size: 20rpx;
        }

        .tips {
            font-size: 22rpx;
        }

        .update {
            padding: 3rpx 10rpx 1rpx;
            color: rgba(0, 0, 0, 0.8);
            border: 1rpx solid rgba(0, 0, 0, 0.8);
            margin-right: 10rpx;
            border-radius: 30rpx;
        }

        .settings {
            position: absolute;
            bottom: 0;
            right: 40rpx;
            font-size: 30rpx;
            color: #fff;
        }
    }
</style>