

var app = new Vue({
  el: "#app",
  data: {
    chart: null,
    city: "",
    dates: [],
    temps: [],
    loading: false,
    errored: false   
  },
  methods: {
    getData: function(accessToken) {
        
      var getCookie = function(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
      };
        this.loading = true;
        if(getCookie("accessToken")==null || getCookie("accessToken")==""){
          var refreshToken = function() {
            axios.post(
                '/auth/signUp', {
                headers: {
                  'accept': 'application/json', 
                  'Content-Type': 'application/json; charset=utf-8'
                }, body: {
                  'password': 'yukinim',
                  'username': 'pw1234'
                }
            } ).then(function(response){
			var setCookie = function(name,value,days) {
                var expires = "";
                if (days) {
                    var date = new Date();
                    date.setTime(date.getTime() + (days*24*60*60*1000));
                    expires = "; expires=" + date.toUTCString();
                }
                document.cookie = name + "=" + (value || "")  + expires + "; path=/";
              };
              setCookie("accessToken",response.data.result,7);
              //alert(getCookie("accessToken"));
            }); 
          };

          refreshToken();
          return;
        } 
          

        if (this.chart != null) {
          this.chart.destroy();
        }
          axios
              .get('/api/v1/test/getInfluxData', {
                headers: {
                    'accept': 'application/json', 
                    'Authorization': getCookie("accessToken"),
                    'Content-Type': 'application/json; charset=utf-8'
                }
              })
              .then(response => {                
                //alert(JSON.stringify(response.data.results[0].series[0].values));
                this.dates = [];
                this.temps = [];

                for(let i = 0 ; i < response.data.results[0].series[0].values.length; i ++){
                  // alert(response.data.results[0].series[0].values[i][0]);
                  this.dates.push(i);
                  this.temps.push(response.data.results[0].series[0].values[i][1]);
                }
                // alert(this.temps.length);
                var ctx = document.getElementById("myChart");
                this.chart = new Chart(ctx, {
                  type: "line",
                  data: {
                    labels: this.dates,
                    datasets: [
                      {
                        label: "Available Bytes (win_mem in telegraf)",
                        backgroundColor: "rgba(255, 255, 255, 0.5)",
                        borderColor: "rgb(255, 50, 50)",
                        fill: false,
                        data: this.temps
                      }
                    ]
                  },
                  options: {
                    title: {
                      display: true,
                      text: "Welcome to My World !"
                    },
                    tooltips: {
                      callbacks: {
                        label: function(tooltipItem, data) {
                          var label =
                            data.datasets[tooltipItem.datasetIndex].label || "";

                          if (label) {
                            label += ": ";
                          }

                          label += Math.floor(tooltipItem.yLabel);
                          return label + "°F";
                        }
                      }
                    },
                    scales: {
                      xAxes: [
                        {
                          type: "time",
                          time: {
                            unit: "hour",
                            displayFormats: {
                              hour: "D"
                            },
                            tooltipFormat: "D"
                          },
                          scaleLabel: {
                            display: true,
                            labelString: "Time"
                          }
                        }
                      ],
                      yAxes: [
                        {
                          scaleLabel: {
                            display: true,
                            labelString: "Available"
                          },
                          ticks: {
                            callback: function(value, index, values) {
                              return value + " Byte";
                            }
                          }
                        }
                      ]
                    }
                  }
                });
              })
              .catch(error => {
                console.log(error);
              
                this.errored = true;
              })
              .finally(() => (this.loading = false));
        // });
        return;
    }
  }
});
