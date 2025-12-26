<template>
	<view>
		<u-sticky bgColor="#fff">
		    <u-tabs :list="CategoryList" keyName="categoryName" @click="clickTabs"></u-tabs>
		</u-sticky>
		
		<!-- 图书列表信息 -->
		<u-row customStyle="margin: 10px" v-for="(item, index) in bookList">
		  <u-col span="5">
		    <u--image showLoading :src="item.bookCover" width="122px" height="150px"
		    ></u--image>
		  </u-col>
		  <u-col span="7">
		    <view>{{item.bookTitle}}</view>
		    <view>{{item.bookAuthor}}</view>
		  </u-col>
		</u-row>	
			
		</view>
	</view>
</template>

<script>
	import {getAllCategory} from 'config/category.js';
	import {getBookByCid} from 'config/book.js';
	
	export default {
		data() {
			return {
				CategoryList:[],
				bookList:[],
				
			}
		},
		async onLoad(){
			await getAllCategory().then(res => this.CategoryList = res.data);
			await getBookByCid(1).then(res =>this.bookList=res.data);
		},
		methods: {
			clickTabs(item){
				getBookByCid(item.categoryId).then(res =>this.bookList=res.data);
			},
			toDetail(id){
				//跳转到听书页面
				uni.navigateTo({
					url: `/pages/book/detail?id=${id}`
				})
			}
			
		}
	}
</script>

<style>

</style>
