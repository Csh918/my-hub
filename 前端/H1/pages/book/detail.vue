<template>
	<view class="audio-detail-page">
		<!-- 书籍的封面，标题和作者 -->
		<view class="book-info">
			<image class="book-cover" :src="bookInfo.bookCover" mode="aspectFill"></image>
			<view class="book-title">{{bookInfo.bookTitle}}</view>
			<view class="book-author">{{bookInfo.bookAuthor}}</view>
		</view>
		
		<!-- 简易音频播放器，uniapp自带的音频组件 -->
		<!-- <view class="page-body">
			<view class="page-section page-section-gap" style="text-align: center;">
				<audio style="text-align: left" :src="bookInfo.bookMp3" :poster="bookInfo.bookCover" :name="bookInfo.bookTitle" :author="bookInfo.bookAuthor" :action="audioAction" controls></audio>
			</view>
		</view> -->
		
		<!-- 复杂些的音频播放器 -->
		<view class="audio-player">
			<!-- 没有登录 -->
			<view class="trial-text" v-if="!hasToken">
				免费试听前{{formatTime(trialTime)}}秒
				<text class="trial-hint" v-if="currentTime >= trialTime">(试听结束)</text>
			</view>
			<!-- 登录 -->
			<view class="trial-text" v-else>
				完整播放（已登录）
			</view>
			<!-- 播放器 -->
			<view class="progress-container">
				<view class="progress-actions">
					<!-- 后退15s -->
					<view class="progress-btn" @click="skipBackward">
						<text class="skip-text">退15s</text>
					</view>
					<!-- 滑块区域，就是音频的进度条 -->
					<view class="progress-bar-wrapper">
						<!-- 滑块事件
						     @change 滑块停止拖动后触发，仅触发一次
							 @changing 滑块正在拖动的时候触发，会多次触发
						 -->
						<slider class="progress-slider" :value="currentProgress" :max="100" :min="0" 
						  activeColor="#FFD700" backgroundColor="#E5E5E5" block-size="16" 
						  @change="onProgressChange" @changing="onProgressChanging"/>
					</view>
					<!-- 快进15s -->
					<view class="progress-btn" @click="skipForward">
						<text class="skip-text">进15s</text>
					</view>
				</view>
				<!-- 显示时间信息 -->
				<view class="time-info">
					<text>{{formatTime(currentTime)}}</text>
					<text>{{formatTime(totalTime)}}</text>
				</view>
			</view>
			<!-- 播放|暂停按钮区域 -->
			<view class="play-btn-wrapper">
				<view class="play-btn" @click="togglePlay">
					<text class="play-icon">{{isPlaying? '❚❚' : '▶'}}</text>
				</view>
			</view>
		</view>
		
		<!-- 功能按钮 -->
		<view class="action-buttons">
			<button class="action-btn sharebutton" @click="setSpeed">
				<u-icon name="rewind-right"></u-icon>倍数
			</button>
			<!-- uni-app，提供了很多api，但是有些api微信官方是不支持的，所以没有效果
			     如果后期遇到这种问题，需要自行去微信官网去寻找解决方案
				 
				 微信分享：把按钮的open-type设置成share
				 当点击按钮的时候，会自动触发 onShareAppMessage事件
			 -->
			<button class="action-btn sharebutton" open-type="share">
				<u-icon name="share-square"></u-icon>分享好友
			</button>
		</view>
		
		<!-- 荐语部分 -->
		<view class="recommendation-section">
			<view class="section-title">荐语</view>
			<view class="section-content">{{bookInfo.bookAbs}}</view>
		</view>
	</view>
</template>

<script>
	import {getBookById} from '../../config/book.js';
	export default {
		//微信分享
		onShareAppMessage() {
			//从缓存中获取用户信心
			const userInfo = uni.getStorageSync("userInfo") || {"nickname":"系统"};
			return {
				title: `来自${userInfo.nickname}分享`,
				path: `/pages/book/detail?id=${this.bookInfo.bookId}`,
				imageUrl: `${this.bookInfo.bookCover}`
			}
		},
		data() {
			return {
				bookInfo: {},      //当前书籍的信息
				isPlaying: false,  //是否播放
				currentTime: 0,    //当前播放时间，单位秒
				totalTime: 0,      //音频总时长，单位秒
				trialTime: 30,     //游客试听时长，单位秒
				currentProgress: 0,//进度百分比
				playbackRate: 1.0, //播放倍数
				audioContext: null,//音频上下文
				token: "",
				hasToken: false,   //记录是否登录
				isTrialEnded: false,//试听是否结束
			}
		},
		onLoad(params) {
			//接收上个页面传递过来的参数
			const id = Number(params.id);
			//发送请求去后端，根据id获取图书的详细信息
			getBookById(id).then(res=>{
				this.bookInfo = res.data;
				this.initAudio();
			})
		},
		onShow() {
			//检查token
			const hasToken = this.hasToken;
			this.getTokenFromCache();
			if(!hasToken&&this.hasToken&&this.isTrialEnded){
				this.isTrialEnded = false;
				if(Math.abs(this.currentTime - this.trialTime)<2){
					uni.showToast({
						title: "登录成功，可播放",
						icon: "success",
						duration: 2000
					})
				}
			}
		},
		methods: {
			//初始化音频播放器
			initAudio(){
				//判断是否有音频文件
				if(!this.bookInfo.bookMp3){
					console.warn("音频地址为空，请设置this.bookInfo.bookMp3")
					return
				}
				//判断是否已经有音频上下文，如果有就先销毁
				if(this.audioContext){
					this.destroyAudio();
				}
				//创建音频上下文
				this.audioContext = uni.createInnerAudioContext();
				this.audioContext.src = this.bookInfo.bookMp3;
				this.audioContext.autoplay = false;
				this.audioContext.loop = false;
				this.audioContext.playbackRate = this.playbackRate;
				//监听播放事件
				this.audioContext.onPlay(()=>{
					this.isPlaying = true;
					console.log("开始播放");
				})
				//监听暂停事件
				this.audioContext.onPause(()=>{
					this.isPlaying = false;
					console.log("暂停播放");
				})
				//监听停止事件
				this.audioContext.onStop(()=>{
					this.isPlaying = false;
					this.currentTime = 0;
					this.currentProgress = 0;
					console.log("停止播放");
				})
				//监听播放结束事件
				this.audioContext.onEnded(()=>{
					this.isPlaying = false;
					this.currentTime = this.totalTime;
					this.currentProgress = 100;
					uni.showToast({
						title: "播放完成",
						icon: 'success'
					})
				})
				//监听时间更新事件
				this.audioContext.onTimeUpdate(()=>{
					if(this.audioContext){
						this.currentTime = Math.floor(this.audioContext.currentTime);
						if(this.currentTime>0){
							this.currentProgress=(this.currentTime/this.totalTime)*100;
						}
						//检查是否超过试听时长
						if(!this.hasToken&&this.currentTime >= this.trialTime){
							if(!this.isTrialEnded){
								this.isTrialEnded = true;
								//暂停播放
								this.pausePlayback();
								//弹框提示用户去登录
								this.showTokenRequireDialog();
							}
						}
					}
				})
				//监听音频加载完成
				this.audioContext.onCanplay(()=>{
					if(this.audioContext && this.audioContext.duration){
						this.totalTime = Math.floor(this.audioContext.duration)
					}
				})
				//监听错误事件
				this.audioContext.onError((res)=>{
					uni.showToast({
						title: "音频加载失败",
						icon: 'error'
					})
				})
				//监听等待数据
				this.audioContext.onWaiting(()=>{
					console.log("音频加载中....")
				})
			},
			//播放和暂停
			togglePlay(){
				if(!this.audioContext){
					if(!this.bookInfo.bookMp3){
						uni.showToast({
							title: "请先设置音频地址",
							icon: "none"
						})
						return;
					}
					this.initAudio();
				}
				if(this.isPlaying){
					this.pausePlayback();
				}else{
					this.startPlayback();
				}
			},
			//播放事件
			startPlayback(){
				//重新检查token
				this.getTokenFromCache();
				//检查是否超过试听时长
				if(!this.hasToken && this.currentTime>=this.trialTime){
					this.showTokenRequireDialog();
					return;
				}
				//如果当前时间接近试听时长，再检查权限
				if(!this.checkPlayPermission()){
					this.showTokenRequireDialog();
					return;
				}
				if(this.audioContext){
					//如果超过试听时长，跳转到试听时长位置
					if(!this.hasToken&&this.currentTime>this.trialTime){
						this.seekTo(this.trialTime);
					}
					this.isTrialEnded = false;
					this.audioContext.play();
				}
			},
			//检查是否有播放权限
			checkPlayPermission(){
				if(this.hasToken){
					return true;
				}
				return this.currentTime < this.trialTime;
			},
			//从缓存中获取token
			getTokenFromCache(){
				const token = uni.getStorageSync("token")||"";
				this.token = token;
				this.hasToken = !!token;
				return token;			
			},
			//显示需要登录（token）的提示框
			showTokenRequireDialog(){
				uni.showModal({
					title: "试听已结束",
					content: "免费试听已结束，登录后可继续播放完整内容",
					confirmText: "去登录",
					cancelText: "取消",
					success: (res) => {
						if(res.confirm){
							uni.switchTab({
								url: "/pages/home/home"
							})
						}else{
							//跳转到试听时长的位置
							this.seekTo(this.trialTime);
						}
					}
				})
			},
			//跳转到指定时间
			seekTo(time){
				if(this.audioContext){
					this.audioContext.seek(time);
					this.currentTime = Math.floor(time);
					this.currentProgress = (this.currentTime/this.totalTime)*100;
				}
			},
			//暂停播放
			pausePlayback(){
				if(this.audioContext){
					this.audioContext.pause();
				}
			},
			//销毁音频播放器
			destroyAudio(){
				if(this.audioContext){
					this.audioContext.stop();
					this.audioContext.destroy();
					this.audioContext = null;
				}
			},
			//时间格式化
			formatTime(seconds){
				const minutes = Math.floor(seconds/60);
				const secs = seconds%60;
				return `${String(minutes).padStart(2,'0')}:${String(secs).padStart(2,'0')}`;
			},
			onProgressChange(e) {
				const progress = e.detail.value
				const targetTime = (progress / 100) * this.totalTime
				this.seekTo(targetTime)
			},
			onProgressChanging(e) {
				// 拖动时的实时更新（不实际跳转）
				const progress = e.detail.value
				this.currentProgress = progress
				this.currentTime = Math.floor((progress / 100) * this.totalTime)
			},
            skipBackward() {
				const newTime = Math.max(0, this.currentTime - 15)
				this.seekTo(newTime)
			},
			skipForward() {
				// 如果没有token，不能超过试听时长
				const maxTime = this.hasToken ? this.totalTime : Math.min(this.totalTime, this.trialTime)
				const newTime = Math.min(maxTime, this.currentTime + 15)
				this.seekTo(newTime)

				// 如果尝试快进超过试听时长，提示需要token
				if (!this.hasToken && this.currentTime + 15 > this.trialTime) {
					this.showTokenRequireDialog()
				}
			},
            //设置倍数
			setSpeed() {
				const speeds = [0.5, 0.75, 1.0, 1.25, 1.5, 2.0]
				const speedLabels = ['0.5x', '0.75x', '1.0x', '1.25x', '1.5x', '2.0x']
				const currentIndex = speeds.findIndex(s => s === this.playbackRate)
				uni.showActionSheet({
					itemList: speedLabels,
					success: (res) => {
						const selectedSpeed = speeds[res.tapIndex]
						this.playbackRate = selectedSpeed
						if (this.audioContext) {
							this.audioContext.playbackRate = selectedSpeed
						}
						uni.showToast({
							title: `倍速已设置为${speedLabels[res.tapIndex]}`,
							icon: 'none'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.sharebutton {
		border: none;
		background: transparent;
		font-size: 12px;
		color: gray;
		padding: 0;
		top: 5px;
	}

	.sharebutton::after {
		border: none;
	}

	.audio-detail-page {
		width: 100%;
		min-height: 100vh;
		background-color: #ffffff;
		display: flex;
		flex-direction: column;
	}

	.tab-item {
		position: relative;
		font-size: 32rpx;
		color: #666666;
		padding: 0 8rpx;

		&.active {
			color: #333333;
			font-weight: 500;
		}
	}

	.tab-line {
		position: absolute;
		bottom: -8rpx;
		left: 0;
		right: 0;
		height: 4rpx;
		background-color: #FFD700;
		border-radius: 2rpx;
	}

	.navbar-actions {
		display: flex;
		align-items: center;
	}

	.navbar-icon {
		padding: 8rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.icon-dots {
		font-size: 32rpx;
		color: #333;
		line-height: 1;
	}

	.icon-circle {
		width: 40rpx;
		height: 40rpx;
		border-radius: 50%;
		border: 2rpx solid #333;
		position: relative;
	}

	.icon-circle::after {
		content: '';
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		width: 12rpx;
		height: 12rpx;
		background-color: #333;
		border-radius: 50%;
	}

	.ml-16 {
		margin-left: 32rpx;
	}

	.content-scroll {
		flex: 1;
		padding-bottom: 160rpx;
	}

	.book-info {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 40rpx 32rpx 32rpx;
	}

	.book-cover {
		width: 360rpx;
		height: 360rpx;
		border-radius: 16rpx;
		background-color: #f5f5f5;
		margin-bottom: 32rpx;
	}

	.book-title {
		font-size: 40rpx;
		font-weight: bold;
		color: #333333;
		margin-bottom: 16rpx;
		text-align: center;
	}

	.book-author {
		font-size: 28rpx;
		color: #999999;
	}

	.audio-player {
		padding: 32rpx;
	}

	.trial-text {
		font-size: 24rpx;
		color: #666666;
		text-align: center;
		margin-bottom: 24rpx;
	}

	.progress-container {
		margin-bottom: 32rpx;
	}

	.progress-actions {
		display: flex;
		align-items: center;
		gap: 16rpx;
		margin-bottom: 16rpx;
	}

	.progress-btn {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		min-width: 60rpx;
	}

	.skip-text {
		font-size: 20rpx;
		color: #666666;
		margin-bottom: 4rpx;
	}

	.skip-icon {
		font-size: 24rpx;
		color: #666666;
		line-height: 1;
	}

	.progress-bar-wrapper {
		flex: 1;
		height: 4rpx;
	}

	.progress-slider {
		width: 90%;
		height: 4rpx;
	}

	.time-info {
		display: flex;
		justify-content: space-between;
		font-size: 24rpx;
		color: #666666;
		margin-top: 8rpx;
	}

	.play-btn-wrapper {
		display: flex;
		justify-content: center;
		margin-top: 32rpx;
	}

	.play-btn {
		width: 120rpx;
		height: 120rpx;
		border-radius: 50%;
		background-color: #FFD700;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 4rpx 12rpx rgba(255, 215, 0, 0.3);
	}

	.play-icon {
		font-size: 48rpx;
		color: #333333;
		line-height: 1;
		margin-left: 4rpx;
	}

	.action-buttons {
		display: flex;
		justify-content: space-around;
		padding: 32rpx;
		border-top: 1rpx solid #f0f0f0;
		border-bottom: 1rpx solid #f0f0f0;
	}

	.action-btn {
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 8rpx;
		font-size: 24rpx;
		color: #666666;
	}

	.action-icon {
		font-size: 44rpx;
		line-height: 1;
	}

	.recommendation-section {
		padding: 32rpx;
	}

	.section-title {
		font-size: 32rpx;
		font-weight: bold;
		color: #333333;
		margin-bottom: 24rpx;
	}

	.section-content {
		font-size: 28rpx;
		color: #666666;
		line-height: 1.8;
		text-align: justify;
	}
</style>


