<template>
  <div style="margin-top: 20px">
    <!-- 精选职位 -->
    <div>
      <div class="pageHeader">精选职位</div>
      <div class="layout">
        <div class="card" style="color: #646464;" v-for="item,index in selectedJob" :key="index" @click="$router.push('/index/jobDetail?id=' + item.id)">
          <div style="height:60%;width:100%;">
            <div class="layout">
              <span class="singleLine" style="width: 70%;height: 30px;font-size: 22px;font-weight: bold;flex-direction: row;">{{ item.jobName }}</span>
              <span style="width: 30%;height: 30px;font-size: 20px;color: #f4612d;flex-direction: row;text-align: right;">{{ item.salary }}</span>
            </div>           
            <p style="margin: 10px 0;font-size: 14px">{{ item.companyAddress }}</p>
            <div>
              <el-tag class="tag" v-for="tag in item.requirement.split(',')" :key="tag">
                {{ tag }}
              </el-tag>
            </div>
          </div>
          <hr>
          <div style="height:40%;width:100%;margin-top: 15px;" class="layout">
            <div style="width: 10%">
              <img :src="item.companyImg" width="50" height="50" style="flex-direction: row;align-self: center;"/> 
            </div>         
            <div style="flex-direction: row;width: 80%;padding-left: 10px; ">
              <p style="width: 100%;height:50%; font-size: 15px" class="singleLine">{{ item.companyName }}</p>
              <div class="singleLine">
                <el-tag class="infoTag" v-for="tag in item.companyDescription.split(',')" :key="tag">
                  {{ tag }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 热招职位 -->
    <div>
      <div class="pageHeader">热招职位</div>
      <div class="layout">
        <div class="card" style="color: #646464;" v-for="item,index in hotJob" :key="index" @click="$router.push('/index/jobDetail?id=' + item.id)">
          <div style="height:60%;width:100%;">
            <div class="layout">
              <span class="singleLine" style="width: 70%;height: 30px;font-size: 22px;font-weight: bold;flex-direction: row;">{{ item.jobName }}</span>
              <span style="width: 30%;height: 30px;font-size: 20px;color: #f4612d;flex-direction: row;text-align: right;">{{ item.salary }}</span>
            </div>           
            <p style="margin: 10px 0;font-size: 14px">{{ item.companyAddress }}</p>
            <div>
              <el-tag class="tag" v-for="tag in item.requirement.split(',')" :key="tag">
                {{ tag }}
              </el-tag>
            </div>
          </div>
          <hr>
          <div style="height:40%;width:100%;margin-top: 15px;" class="layout">
            <div style="width: 20%">
              <img :src="item.companyImg" width="50" height="50" style="flex-direction: row;align-self: center;"/> 
            </div>         
            <div style="flex-direction: row;width: 80%;padding-left: 10px;">
              <p style="width: 100%;height:50%; font-size: 15px" class="singleLine">{{ item.companyName }}</p>
              <div class="singleLine">
                <el-tag class="infoTag" v-for="tag in item.companyDescription.split(',')" :key="tag">
                  {{ tag }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props:{
    positionData: Array
  },
  computed: {
    selectedJob() {
      return this.positionData.filter(item => {
        return item.isSelected === true
      })
    },
    hotJob() {
      return this.positionData.filter(item => {
        return item.hotJob === true
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .card {
    border-radius: 10px;
    position: relative;
    padding: 20px;
    width: 31%;
    height: 200px;
    margin-bottom: 15px;
    flex-direction: row;
    background-color: #fff;
    cursor: pointer;
    &:hover {
      border: 1px solid #f4612d;
      box-shadow: 10px 10px 10px rgba(218, 217, 217, 0.5);
    }
  }
  .layout::after{
    content: "";
    /* flex: auto; */
    width: 31%;
  }
</style>