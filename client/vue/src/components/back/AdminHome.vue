<template>
  <div class="HomeBox">
    <el-row class="HomeRow">
      <!-- 左侧 -->
      <el-col :span="12">
        <el-row>
          <div class="box" style="background-color: #ffa755;">
            <div class="textBox">
              <i class="el-icon-user textIcon"></i>
              <div style="float: left;line-height: 45px;margin-left: 20px;">
                <p style="font-size: 45px;">
                  {{ total }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
                </p>
                <p style="font-size: 20px;margin-left: 5px; ">总用户</p>
              </div>
              <i class="el-icon-s-promotion iconContext"></i>
            </div>
          </div>
          <div class="box" style="background-color: #b48dd3;">
            <div class="textBox">
              <i class="el-icon-s-custom textIcon"></i>
              <div style="float: left;line-height: 45px;margin-left: 20px;">
                <p style="font-size: 45px;">
                  {{ companyNum }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
                </p>
                <p style="font-size: 20px;margin-left: 5px; ">企业用户</p>
              </div>
              <i class="el-icon-s-cooperation iconContext"></i>
            </div>
          </div>
        </el-row>
      </el-col>

      <!-- 右侧 -->
      <el-col :span="12">
        <el-row>
          <div class="box" style="background-color: #68e365;">
            <div class="textBox">
              <i class="el-icon-s-management textIcon"></i>
              <div style="float: left;line-height: 45px;margin-left: 20px;">
                <p style="font-size: 45px;">
                  {{ jobNum }}<span style="font-size: 28px;font-weight: bold;">&ensp;条</span>
                </p>
                <p style="font-size: 20px;margin-left: 5px; ">招聘信息</p>
              </div>
              <i class="el-icon-s-opportunity iconContext"></i>
            </div>
          </div>
          <div class="box" style="background-color: #5dcfc6;">
            <div class="textBox">
              <i class="el-icon-user-solid textIcon"></i>
              <div style="float: left;line-height: 45px;margin-left: 20px;">
                <p style="font-size: 45px;">
                  {{ applicantNum }}<span style="font-size: 28px;font-weight: bold;">&ensp;人</span>
                </p>
                <p style="font-size: 20px;margin-left: 5px; ">学生</p>
              </div>
              <i class="el-icon-s-check iconContext"></i>
            </div>
          </div>
        </el-row>
      </el-col>
    </el-row>

    <!-- 折线图 -->
    <div class="chart">
      <div id="line" style="width: 96%; height: 280px; margin: 0 auto; "></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  data() {
    return {
      total: 0,
      form: {},
      form1: {},
      form2: {},
      list: [],
      xData: [],
      sevenDay:[],
      userData: [],
      companyData: [],
      applicantData: [],
      jobNum: 0,
      companyNum: 0,
      applicantNum: 0,
    }
  },
  // created() {
  //   // this.getInfo()
  //   // this.getNowTime()
  // },
  mounted() {
    //折线图、柱状图
    var chartDom = document.getElementById('line')
    var myChart = echarts.init(chartDom)
    var option = {
      title: {
        text: '近一周用户注册数量统计',
        // textStyle: {
        //   fontWeight: 'normal',             
        //   color: '#A3FFFC'  //标题颜色
        // },
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['总用户', '企业用户', '学生'],
        textStyle: {
          fontWeight: 'normal',              
          color: '#000'   //legend颜色
        },
      },
      grid: {
        left: '5%',
        right: '8%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.sevenDay,
        axisLabel: {
          textStyle: {
            color: '#000',
            fontSize:'16'
          },
        },  
      },
      yAxis: {
        axisLabel: {
          textStyle: {
              color: '#000',
              fontSize:'12'
          }
        }, 
      },
      series: [
        {
          name: '总用户',
          type: 'line',
          // data: this.userData
          data: [7,3,5,2,4,6,9]
        },
        {
          name: '企业用户',
          type: 'line',
          // data: this.companyData
          data: [3,0,1,1,0,2,4]
        },
        {
          name: '学生',
          type: 'line',
          // data: this.applicantData
          data: [4,3,4,1,4,4,5]
        }]
    }
    this.request.get("/job").then(res => {
      this.jobNum = res.data.length
    }),
    this.request.get("/user").then(res => {
      this.total = res.data.length - 1
      this.list = res.data
      this.getSevenDay()
      this.sevenDay.reverse()

      // 统计企业、实习生人数
      this.list.forEach(item => {
        if(item.role === '企业'){
          this.companyNum++
        }
        if(item.role === '学生'){
          this.applicantNum++
        }
      })
      // 按照时间进行排序
      this.sortArr(this.list)

      // 统计数据
      var result = this.list.filter(item => {
        return this.sevenDay.indexOf(this.editTime(item.createTime)) != -1
      })
      var companyResult = result.filter(item => {
        return item.role === '企业'
      })
      var applicantResult = result.filter(item => {
        return item.role === '实习生'
      })

      // 处理数据
      this.form   = this.SetResult(result)
      this.form1  = this.SetResult(companyResult)
      this.form2  = this.SetResult(applicantResult)

      // 数据排序
      this.form   = this.SetDataSort(this.form)
      this.form1  = this.SetDataSort(this.form1)
      this.form2  = this.SetDataSort(this.form2)

      this.setData(this.form,this.userData)
      this.setData(this.form1,this.companyData)
      this.setData(this.form2,this.applicantData)

      window.onresize = function (ec) { // 监听窗口大小变化
        // console.log(ec)
        myChart.resize()       // 自适应大小变化
      }

      myChart.setOption(option);
    })
    
  },
  methods: {
    // 获取当前时间
    getNowTime() {
      var date = new Date();
      //年 getFullYear()：四位数字返回年份
      var year = date.getFullYear(); //getFullYear()代替getYear()
      //月 getMonth()：0 ~ 11
      var month = date.getMonth() + 1;
      //日 getDate()：(1 ~ 31)
      var day = date.getDate();
      //时 getHours()：(0 ~ 23)
      var hour = date.getHours();
      //分 getMinutes()： (0 ~ 59)
      var minute = date.getMinutes();
      //秒 getSeconds()：(0 ~ 59)
      var second = date.getSeconds();
      var time = year + '-' + this.addZero(month) + '-' + this.addZero(day) + ' ' + this.addZero(hour) + ':' + this.addZero(minute) + ':' + this.addZero(second);
      return time;
    },
    // 小于10的拼接上0字符串
    addZero(s) {
      return s < 10 ? ('0' + s) : s;
    },
    // 返回最近七天的日期
	  getSevenDay() {
      // let days = [];
      for(let i = 0;i <= 24 * 6;i += 24) {		//今天加上前6天       
        let day = new Date();
        //使用当天时间戳减去以前的时间毫秒（小时*分*秒*毫秒）
        let dateItem = new Date(day.getTime() - i * 60 * 60 * 1000)	
        let y = dateItem.getFullYear()	        //获取年份
        let m = dateItem.getMonth() + 1	        //获取月份js月份从0开始，需要+1
        let d= dateItem.getDate()	              //获取日期
        m = this.addZero(m)	                    //给为单数的月份补零
        d = this.addZero(d)	                    //给为单数的日期补零
        let valueItem = y + '-' + m + '-' + d   //组合
        // days.push(valueItem)	                  //添加至数组
        this.sevenDay.push(valueItem)       
      }
      // console.log(this.sevenDay)
      return this.sevenDay		
    },
    // 给日期加0
    addDate0(time) {
      if (time.toString().length == 1) {
        time = '0' + time.toString();
      }
      return time;
    },
    // 处理时间数据
    editTime(val) {
      return val.substring(0, 10)
    },
    // 所有数据时间排序
    sortArr(arr, attr ='createTime',flag=true) {
      for (var i = 0; i < arr.length - 1; i++) {//比较arr.length-1轮
        for (var j = i + 1; j < arr.length; j++) {
          if (arr[i][attr] > arr[j][attr] && flag == true) {//交换
            var temp = arr[i][attr]//临时变量
            arr[i][attr] = arr[j][attr]
            arr[j][attr] = temp
          }else if(arr[i][attr] < arr[j][attr] && flag == false){
            var temp = arr[i][attr]//临时变量
            arr[i][attr] = arr[j][attr]
            arr[j][attr] = temp
          }
        }
      }
      return arr
    },
    SetResult(res) {
      return res.reduce((obj, name) => {
        if (this.editTime(name.createTime) in obj) {
          obj[this.editTime(name.createTime)]++
        } else {
          obj[this.editTime(name.createTime)]=1
        }
          return obj
      },{})
    },
    SetDataSort(form) {
      var arr=[];
      var newData={}
      var restDay = []
      var dateList = []

      for(var key in form){
        dateList.push(key)
      }
      
      for(var key in this.sevenDay){
        if(dateList.indexOf(this.sevenDay[key]) === -1) {
          restDay.push(this.sevenDay[key])
        }
      }

      for(var key in restDay){
        form[restDay[key]] = 0
      }
      
      for(var key in form){
        arr.push(key)
      }
      arr = arr.sort()
    
      for(var i in arr){
          var itemKey = arr[i]
          newData[itemKey] = form[itemKey]
      }
      form = newData
      return form
    },
    setData(form,data) {
      for (let key in form) {
        data.push(form[key])    
      }
    }    
  }
}
</script>

<style>
  .HomeBox {
    background-color: #f6f6f6;
    /* background-color: pink; */
    /* height: 641.6px; */
    width: 100%;
    height: 100%;
    top:0;
    left: 0;
    position: absolute;
  }
  .el-row {
    padding: 0;
  }
  .HomeRow {
    margin-top: 20px;
  }
  .HomeRow .box {
    width: 92%;
    height: 130px;
    margin: 0 auto 20px;
    line-height: 130px;
    border-radius: 20px;
    padding-top: 20px;
    overflow: hidden; 
    position: relative;
    background: #d3dce6;
  }
  .HomeRow .textBox {
    width: 80%;
    height: 90px;
    line-height: 90px;
    color: #fff;
    margin: auto;  
  }
  .HomeRow .textBox .textIcon {
    background-color: rgba(255,255,255,0.4);
    width: 70px;
    height: 70px;
    line-height: 70px;
    text-align: center;
    font-size: 40px;
    margin-top: 10px;
    float: left;
    border-radius: 50%;
  }
  .HomeRow .textBox .iconContext {
    font-size: 120px;
    opacity: 30%;
    color: #fff;
    top: 0px;
    right: 0;
    position: absolute;
  }
  .chart {
    width: 96%;
    height: 300px;
    margin: 0 auto;
    border-radius: 20px;
    padding-top: 15px;
    background-color: #fff;
  }
</style>