<template>
  <el-card>
    <el-row class="tac">
      <el-col :span="12">
        <div id="studyChart" :style="{width: '480px', height: '400px'}"></div>
      </el-col>
      <el-col :span="12">
        <div id="homeworkChart" :style="{width: '400px', height: '400px'}"></div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Statistics",
  data(){
    return{
    }
  },
  mounted() {
    this.drawLineStudy()
    this.drawHomeWork()
  },
  methods:{
    drawLineStudy(){
      let studyChart= this.$root.echarts.init(document.getElementById("studyChart"))
      let option = {
        color: ['#7cf4ff'],
        title: {
          text: '学习统计'
        },
        legend: {
          data: ['学习统计']
        },
        radar: [
          {
            indicator: [
              { text: '讨论次数', max: 20 },
              { text: '作业平均成绩', max: 5 },
              { text: '作业提交次数', max: 10 },
              { text: '课程数量', max: 10 },
              { text: '考试平均成绩', max: 100 },
            ],
            center: ['55%', '45%'],
            radius: 120,
            name: {
              textStyle: {
                color: '#fff',
                backgroundColor: '#666',
                borderRadius: 3,
                padding: [3, 5]
              }
            }
          }
        ],
        series: [
          {
            name: '学习统计',
            type: 'radar',
            emphasis: {
              lineStyle: {
                width: 4
              }
            },
            data: [
              {
                value: [30, 3.5, 10, 10, 20],
                name: '学习统计',
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value;
                  }
                },
                areaStyle: {
                  color: 'rgba(72,234,150,0.6)'
                }
              }
            ]
          }
        ]
      };
      request.get("/statistic").then( res => {
        option.series[0].data[0].value[0] = res.data.discussCount
        option.series[0].data[0].value[1] = res.data.homeworkScore
        option.series[0].data[0].value[2] = res.data.homeworkSubmitCount
        option.series[0].data[0].value[3] = res.data.courseCount
        option.series[0].data[0].value[4] = res.data.examScore
        studyChart.setOption(option)
      })
    },
    drawHomeWork(){
      let homeworkChart= this.$root.echarts.init(document.getElementById("homeworkChart"))
      let option = {
        title: {
          text: '作业统计'
        },
        series: [{
          type: 'gauge',
          startAngle: 90,
          endAngle: -270,
          pointer: {
            show: false
          },
          progress: {
            show: true,
            overlap: false,
            roundCap: true,
            clip: false,
            itemStyle: {
              borderWidth: 1,
              borderColor: '#464646'
            }
          },
          axisLine: {
            lineStyle: {
              width: 40
            }
          },
          splitLine: {
            show: false,
            distance: 0,
            length: 10
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false,
            distance: 50
          },
          data: [{
            value: 20,
            name: '优秀',
            title: {
              offsetCenter: ['0%', '-40%']
            },
            detail: {
              valueAnimation: true,
              offsetCenter: ['0%', '-25%']
            }
          },
            {
              value: 40,
              name: '良好',
              title: {
                offsetCenter: ['0%', '-5%']
              },
              detail: {
                valueAnimation: true,
                offsetCenter: ['0%', '10%']
              }
            },
            {
              value: 60,
              name: '合格',
              title: {
                offsetCenter: ['0%', '30%']
              },
              detail: {
                valueAnimation: true,
                offsetCenter: ['0%', '45%']
              }
            }
          ],
          title: {
            fontSize: 14
          },
          detail: {
            width: 50,
            height: 14,
            fontSize: 14,
            color: 'auto',
            borderColor: 'auto',
            borderRadius: 20,
            borderWidth: 1,
            formatter: '{value}%'
          }
        }]
      };
      request.get('/statistic/homework').then( res => {
        option.series[0].data[0].value = res.data[0]*100
        option.series[0].data[1].value = res.data[1]*100
        option.series[0].data[2].value = res.data[2]*100
        homeworkChart.setOption(option)
      })
    }
  }
}
</script>

<style scoped>

</style>
