<template>
  <div id="app">
    <b-navbar toggleable="lg" type="dark" variant="info" fixed="top" style="z-index: 999">
      <b-navbar-brand href="/">青橙影院</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item v-for="item in this.parentType" :key="item.type_id"
                      :to="{name: 'list', query: {typeId: item.id, typeName: item.name}}" @click="isCategoryVisible =
                       true">
            {{ item.name }}
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-form-input v-model="kw"  size="sm" class="mr-sm-2" placeholder="影视搜索"
                          @keydown.enter.native="listenEnterKey"/>
          </b-nav-form>
          <b-nav-form>
            <b-button size="sm" class="my-2 my-sm-0" @click="search">搜索</b-button>
          </b-nav-form>
<!--      <b-nav-item href="https://www.github.com/sazima/video_website" target="_blank"> GitHub </b-nav-item>-->
<!--          <b-nav-item v-if="!userInfo" v-b-modal="'loginModal'">-->
<!--            <span>登录</span>-->
<!--          </b-nav-item>-->
<!--          <b-nav-item-dropdown right v-else>-->
<!--            <template v-slot:button-content>-->
<!--              <span>{{ userInfo.nickName }}</span>-->
<!--            </template>-->
<!--            <b-dropdown-item :to="{name: 'collect'}">采集</b-dropdown-item>-->
<!--            <b-dropdown-item href="#">更多功能， 敬请期待</b-dropdown-item>-->
<!--            <b-dropdown-item href="#" @click="signOut">退出</b-dropdown-item>-->
<!--          </b-nav-item-dropdown>-->
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>


    <div v-show="isCategoryVisible" class="category-header">
      <h6 style="margin-top: 60px;margin-left: 30px;">分类:
        <span style="margin-left: 32px;" v-if="multiAttributes.genre">{{multiAttributes.genre}}</span>
        <span v-if="multiAttributes.region">-{{ multiAttributes.region }}</span>
        <span v-if="multiAttributes.year">-{{ multiAttributes.year }}</span>
      </h6>
      <b-button @click="showCategory = !showCategory" variant="blue" class="category-button">
        展开
        <b-icon icon="chevron-down"></b-icon> <!-- 添加的展开按钮图标 -->
      </b-button>
    </div>

    <!-- 折叠面板 -->
    <b-collapse id="nav-collapse-category" v-model="showCategory" class="category-collapse">
          <div class="flex-container">
            <h6 style="margin-right: 20px;">按剧情:</h6>
            <ul class="horizontal-list">
              <li v-for="genre in genres" :key="genre" @click="handleCategoryClick('genre',genre)">{{ genre }}</li>
            </ul>
          </div >
          <div class="flex-container">
            <h6 style="margin-right: 20px;">按地区:</h6>
            <ul class="horizontal-list">
              <li v-for="region in regions" :key="region" @click="handleCategoryClick('region',region)">{{ region }}</li>
            </ul>
          </div>
          <div class="flex-container">
            <h6 style="margin-right: 20px;">按年份:</h6>
            <ul class="horizontal-list">
              <li v-for="year in years" :key="year" @click="handleCategoryClick('year',year)">{{ year }}</li>
            </ul>
          </div>
    </b-collapse>


    <router-view/>
<!--    <b-modal id="loginModal" title="登录"-->
<!--             ok-title="登录"-->
<!--             cancel-title="取消"-->
<!--             @ok="login"-->
<!--    >-->
<!--      <b-container fluid>-->
<!--        <b-row align-h="center">-->
<!--          <b-form-input :cols="12" sm="12" lg="12" v-model="loginForm.email" placeholder="邮箱"></b-form-input>-->
<!--          <b-form-input required cols="12" sm="12" lg="12" v-model="loginForm.password" placeholder="密码" type="password"-->
<!--                        style="margin-top: 10px"></b-form-input>-->
<!--        </b-row>-->
<!--        <b-row @click="showRegisterModal">-->
<!--          <span style="margin-top: 20px">-->
<!--          还没有帐号, 点击注册-->
<!--          </span>-->
<!--        </b-row>-->
<!--      </b-container>-->
<!--    </b-modal>-->
<!--    <b-modal id="registerModal" title="注册"-->
<!--             ok-title="注册"-->
<!--             cancel-title="取消"-->
<!--             @ok="register"-->
<!--    >-->
<!--      <b-container fluid>-->
<!--        <b-row align-h="center">-->
<!--          <b-form style="width: 100%">-->
<!--            <b-form-input :state="validationEmail" v-model="registerForm.email"-->
<!--                          placeholder="邮箱" style="margin-top: 10px"></b-form-input>-->
<!--            <b-form-invalid-feedback :state="validationEmail">-->
<!--              输入合法邮箱-->
<!--            </b-form-invalid-feedback>-->
<!--          </b-form>-->
<!--          <b-form style="width: 100%">-->
<!--            <b-form-input :state="validationNickName" v-model="registerForm.nickName"-->
<!--                          placeholder="昵称" style="margin-top: 10px"></b-form-input>-->
<!--            <b-form-invalid-feedback :state="validationNickName">-->
<!--              输入昵称-->
<!--            </b-form-invalid-feedback>-->
<!--          </b-form>-->
<!--          <b-form style="width: 100%">-->
<!--            <b-form-input cols="12" sm="12" lg="12" v-model="registerForm.password" placeholder="密码" type="password"-->
<!--                          style="margin-top: 10px"></b-form-input>-->
<!--          </b-form>-->
<!--          <b-form style="width: 100%">-->
<!--            <b-form-input :state="validateConfirmPassword" cols="12" sm="12" lg="12"-->
<!--                          v-model="registerForm.confirmPassword" placeholder="确认密码" type="password"-->
<!--                          style="margin-top: 10px"></b-form-input>-->
<!--            <b-form-invalid-feedback :state="validateConfirmPassword">-->
<!--              与密码一致-->
<!--            </b-form-invalid-feedback>-->
<!--          </b-form>-->
<!--        </b-row>-->
<!--      </b-container>-->
<!--    </b-modal>-->
  </div>
</template>


<script>


import {getTypes} from "@/apis/video";


export default {
  name: "layout",
  data(){
    return{
      parentType:[],
      isCategoryVisible: false,
      showCategory: false,
      genres: ['喜剧', '爱情', '恐怖', '动作', '科幻', '剧情', '战争', '警匪', '犯罪', '动画', '奇幻', '冒险'],
      regions: ['大陆', '香港', '台湾', '欧美', '韩国', '日本', '泰国', '印度', '俄罗斯', '其他'],
      years: [2024, 2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014, 2013, 2012],
      multiAttributes: {
        genre: '',
        region: '',
        year: ''
      }
    }
  },
  methods:{
    parseTypeList(all_type){
        for (let type_item of all_type){
          if(type_item.parentType===0){
            this.parentType.push(type_item)
          }
        }
    },
    getTypes(){
      const allType = sessionStorage.getItem('allType')
      if(allType){
        return this.parseTypeList(JSON.parse(allType))
      }
      //下面的getTypes是发送请求，上面的getTypes是整理出全部type的方法
      getTypes().then(allType=>{
        sessionStorage.setItem('allType', JSON.stringify(allType))
        this.parseTypeList(allType)
      })
    },
    search(){
      if(!this.kw){
        return
      }
      this.$router.push({
        path:"/list",
        query:{kw:this.kw}
      }).catch(err=>{
        console.log(err)
        location.reload()
      })
    },
    listenEnterKey(event){
      console.log(event.which)
      if(event.which==13){
        event.preventDefault()
        this.search()
      }
    },
    handleCategoryClick(type, value) {
      this.multiAttributes[type] = value;
    },

  },

  mounted() {
    this.getTypes()
  }

}
</script>

<style scoped>

.flex-container {
  margin-left: 30px;
  display: flex;
  align-items: baseline;
}

.horizontal-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-wrap: wrap;
}

.horizontal-list li {
  margin-right: 10px;
  margin-bottom: 10px;
  cursor: pointer;
}

.category-collapse {
  margin-top: 0px; /* Adjust this value as needed */
  padding: -0px;
}

.category-button {
  margin-top: 60px;

}

.horizontal-list li:hover {
  color: blue;
}

.category-header {
  display: flex;
  justify-content: space-between; /* 将子元素分布在行上，两端对齐 */
  align-items: center; /* 垂直居中对齐 */
}

.selected {
  color: blue;
}
</style>