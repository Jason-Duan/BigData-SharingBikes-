//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    longitude:0,
    latitude:0,
    //控件的数组
    controls:[],
    markers: []
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.getLocation({
      success: function(res) {
        //经度
        var longitude = res.longitude
        //维度
        var latitude = res.latitude
        //console.log(longitude+" "+latitude)
        //把实际的经纬度赋值给当前对象的拷贝
        that.setData({
          longitude: longitude,
          latitude: latitude
        })
        //查找单车
        findBikes(that, longitude, latitude)
      },
    }),
    //获取当前的设备信息
    wx.getSystemInfo({
      success: function(res) {
        var width = res.screenWidth
        var height = res.screenHeight
        //添加控件
        that.setData({
          controls: [
            {
              //中心点位置
              id: 1,
              iconPath: '/images/location.png',
              position: {
                width: 20,
                height: 35,
                left: width / 2 - 10,
                top: height / 2 - 65.
              },
              //是否可点击
              clickable: true
            },{
              //定位按钮安置
              id: 2,
              iconPath: '/images/img1.png',
              position: {
                width: 40,
                height: 40,
                left: 10,
                top: height - 110.
              },
              //是否可点击
              clickable: true
            }, {
              //扫码按钮
              id: 3,
              iconPath: '/images/qrcode.png',
              position: {
                width: 100,
                height: 40,
                left: width / 2 - 50,
                top: height - 110.
              },
              //是否可点击
              clickable: true
            }, {
              //充值按钮
              id: 4,
              iconPath: '/images/pay.png',
              position: {
                width: 40,
                height: 40,
                left: width - 45,
                top: height - 110.
              },
              //是否可点击
              clickable: true
            }, {
              //手动添加一辆单车
              id: 5,
              iconPath: '/images/bike.png',
              position: {
                width: 35,
                height: 40,
              },
              //是否可点击
              clickable: true
            }, {
              //报修
              id: 6,
              iconPath: '/images/warn.png',
              position: {
                width: 35,
                height: 35,
                left: width - 42,
                top: height - 145.
              },
              //是否可点击
              clickable: true
            }
          ]
        })
      },
    })
  },

  controltap(e) {
    var that = this
    var cid = e.controlId;
    switch(cid) {
      case 1: {
        console.log(111111111)
        break
      }
      case 2: {
        this.mapCtx.moveToLocation()
        break
      }
      case 3: {
        var status = app.globalData.status
        wx.navigateTo({
          url: '../'+ status + "/" + status,
        })
        break
      }
      case 4: {
        //跳转到充值页面
        wx.navigateTo({
          url: '../recharge/recharge',
        })
        break
      }
      case 5: {
        //添加车辆的功能（获取到移动后的位置）
        that.mapCtx.getCenterLocation({
          success: function(res) {
            var log = res.longitude
            var lat = res.latitude
            wx.request({
              url: "http://localhost:9999/bike",
              header: {
                'content-type': 'application/x-www-form-urlencoded'
              },
              method: 'POST',
              data: {
                location: [
                  log,
                  lat
                ]
              },
              success: function(res) {
                //添加成功后，再查找单车
                findBikes(that, log, lat)
              }
            })
          },
        })
        break
      }
      case 6: {
        console.log(666666666666)
        break
      }
      default: break
    }
  },

  //拖动地图事件
  regionchange: function(e) {
    var that = this
    //获取拖动之后的经纬度
    if(e.type == 'end') {
      that.mapCtx.getCenterLocation({
        success: function(res) {
          //查找拖动后的所在位置附近的单车
          var longitude = res.longitude
          var latitude = res.latitude
          findBikes(that, longitude, latitude)
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.mapCtx = wx.createMapContext('map')
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})


function findBikes(that, longitude, latitude) {
  //查找单车
  wx.request({
    url: "http://localhost:9999/bikes",
    header: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    method: "GET",
    //查询条件，当前位置
    data: {
      longitude: longitude,
      latitude: latitude
    },
    success: function (res) {
      //console.log(res)
      //函数式编程，就是将每一个bike拿出来进行处理
      const bikes = res.data.map((geoRes) => {
        return {
          id: geoRes.content.id,
          iconPath: "/images/bike.png",
          width: 35,
          height: 40,
          longitude: geoRes.content.location[0],
          latitude: geoRes.content.location[1]
        };
      })
      //console.log(bikes)
      //将整理好的bikes数据set到data中
      that.setData({
        markers: bikes
      })
    }
  })
}
