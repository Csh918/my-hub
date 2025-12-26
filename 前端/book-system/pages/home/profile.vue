<template>
	<view class="login-container">
		<!-- 头像选择区域 -->
		<view class="avatar-uploader" @click="chooseAvatar">
			<image class="avatar" :src="userInfo.avatar" mode="widthFix"></image>
		</view>

		<!-- 昵称区域 -->
		<view class="nickname-input">
			<input type="text" placeholder="请输入昵称" v-model="userInfo.nickname" placeholder-style="color: #999;" />
		</view>
		<!-- 修改按钮 -->
		<button class="login-btn" @click="handleNickname">修改</button>
		
	</view>
</template>

<script>
	import {uploadAvatarUrl} from 'config/login.js';
	export default {
		data() {
			return {
				userInfo: {
					avatar: '',
					nickname: ''
				}
			};
		},
		onLoad() {
			this.userInfo = uni.getStorageSync('userInfo');
		},
		
		methods: {
			chooseAvatar() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original','compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						const tempFilePath = res.tempFilePaths[0];
						this.userInfo.avatar = tempFilePath;
						this.uploadAvatar(tempFilePath);
					}
				})
			},
			uploadAvatar(tempPath){
				uploadAvatarUrl({avatar: tempPath}).then(res =>{
					uni.setStorageSync('userInfo',res.data)
				})
			},
			handleNickname(){
				uploadAvatarUrl({nickname: this.userInfo.nickname}).then(res=>{
					uni.setStorageSync('userInfo',res.data);
					uni.switchTab({
						url:'/pages/home/home'
					})
				})
			}
		}
	};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 100rpx 50rpx;
}

.avatar-uploader {
  width: 200rpx;
  height: 200rpx;
  border-radius: 50%;
  border: 1px dashed #ccc;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 60rpx;
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.avatar-uploader text {
  font-size: 28rpx;
  color: #999;
  margin-top: 20rpx;
}

.nickname-input {
  width: 100%;
  margin-bottom: 80rpx;
}

.nickname-input input {
  width: 100%;
  height: 80rpx;
  border: 1px solid #eee;
  border-radius: 10rpx;
  padding: 0 20rpx;
  font-size: 32rpx;
}

.login-btn {
  width: 100%;
  height: 100rpx;
  line-height: 100rpx;
  background-color: #07C160;
  color: #fff;
  border-radius: 50rpx;
  font-size: 36rpx;
}

.login-btn:disabled {
  background-color: #ccc;
}
</style>