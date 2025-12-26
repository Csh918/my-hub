<template>
	<view>
		<!-- 滚动通告 -->
		<u-notice-bar :text="notice" mode="closable" fontSize=15></u-notice-bar>
		<!-- 轮播图 -->
		<u-swiper :list="configList" keyName="configUrl" showTitle autoplay circular></u-swiper>
		<!-- 新书推荐 -->
		<view class="Latest-book">
			<u--text text="新书推荐榜"></u--text>

			<u-scroll-list @right="right" @left="left">
				<view class="scroll-list" style="flex-direction: row;">
					<view class="scroll-list__goods-item" v-for="(item, index) in bookList" :key="index"
						:class="[(index === 9) && 'scroll-list__goods-item--no-margin-right']" @click="toDetail(item.bookId)">
						<image class="scroll-list__goods-item__image" :src="item.bookCover"></image>
						<text class="scroll-list__goods-item__text">{{ item.bookTitle}}</text>
					</view>
					<view class="scroll-list__show-more">
						<text class="scroll-list__show-more__text">查看更多</text>
						<u-icon name="arrow-leftward" color="#f56c6c" size="15"></u-icon>
					</view>
				</view>
			</u-scroll-list>
		</view>
	</view>
</template>

<script>
	import {getLatestNotice} from 'config/notice.js';
	import {getAllConfig} from 'config/config.js';
	import {getLatestBook} from 'config/book.js';
	export default {
		data() {
			return {
				notice: '',
				configList: [],
				bookList: []
			}

		},
		async onLoad() {
			// 获取最新的系统公告
			await getLatestNotice().then(res => this.notice = res.data.noticeContent);

			// 获取轮播图列表
			await getAllConfig().then(res => {
				// 通过map遍历，解构原对象并把原configTitle属性名修改成title，轮播图默认显示title属性
				this.configList = res.data.map(item => {
					const {
						configTitle,
						...rest
					} = item;
					return {
						...rest,
						title: configTitle
					}
				});
			});

			await getLatestBook(1,6).then(res => this.bookList = res.data);
		},
		methods: {
			toDetail(id){
				uni.navigateTo({
					url:`/pages/book/detail?id=${id}`
				})
			}
		}
	}
</script>

<style lang="scss">
	
	.Latest-book{
		width:100%;
		height: 450rpx;
		background-color: lightgoldenrodyellow;
		border-radius: 15px;
		padding: 10px;
		margin: 10px auto;
	}
	.scroll-list {
		@include flex(column);
		text-align: center;
		margin-top: 20rpx;

		&__goods-item {
			margin-right: 20px;

			&__image {
				width: 215rpx;
				height: 300rpx;
				border-radius: 4px;
			}

			&__text {
				color: #000000;
				text-align: center;
				font-size: 12px;
				margin-top: 5px;
			}
		}

		&__show-more {
			background-color: #ffff7f;
			width: 45px;
			border-radius: 5px;
			@include flex(column);
			align-items: center;

			&__text {
				font-size: 12px;
				font-weight: bold;
				width: 15px;
				color: #f56c6c;
				line-height: 16px;
				margin-top: 45px;
				padding-right: 8px;
			
			}
		}
	}
</style>