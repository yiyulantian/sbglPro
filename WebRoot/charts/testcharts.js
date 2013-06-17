var chart5;
        $(document).ready(function() {
            chart5 = new Highcharts.Chart({
                chart: {
                    renderTo: 'container5',
                    type: 'column'
                },
                title: {
                    text: '信息统计表'
                },
                subtitle: {
                    text: '柱状显示1'
                },
                xAxis: {
                    categories: [
                        '个人用户',
                        '公交公司',
                        '货运公司',
                        '物流公司',
                    ]
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '(个)'
                    }
                },
                legend: {
                    layout: 'vertical',
                    backgroundColor: '#FFFFFF',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 100,
                    y: 70,
                    floating: true,
                    shadow: true
                },
                tooltip: {
                    formatter: function() {
                        return ''+
                            this.x +': '+ this.y +' 个';
                    }
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                    series: [{
                    name: '总客户数',
                    data: [47,2,3,7]
        
                }, {
                    name: '总监控车辆数',
                    data: [59,324,74,109]
        
                }]
            });
            $('#button5').click(function() {
                chart5.exportChart();
        	  });
        });

            var chart1;
            $(document).ready(function() {
                chart1 = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container1',
                        type: 'bar'
                    },
                    title: {
                        text: '信息统计表'
                    },
                    subtitle: {
                        text: '柱状显示2'
                    },
                    xAxis: {
                        categories: ['个人用户',
                                     '公交公司',
                                     '货运公司',
                                     '物流公司',],
                        title: {
                            text: null
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '(个)',
                            align: 'high'
                        },
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    tooltip: {
                        formatter: function() {
                            return ''+
                                this.series.name +': '+ this.y +' 个';
                        }
                    },
                    plotOptions: {
                        bar: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -100,
                        y: 100,
                        floating: true,
                        borderWidth: 1,
                        backgroundColor: '#FFFFFF',
                        shadow: true
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                    	name: '总客户数',
                        data: [47,2,3,7]
                    }, {
                    	 name: '总监控车辆数',
                         data: [59,324,74,109]
                    }]
                });
            });
