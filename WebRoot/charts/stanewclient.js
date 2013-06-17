var chart3;
$(document).ready(
		function() {
			chart3 = new Highcharts.Chart( {
				chart : {
					renderTo : 'newclientchart',
					type : 'spline'
				},
				title : {
					text : ''
				},
				//subtitle : {
				//	text : 'An example of irregular time data in Highcharts JS'
				//},
				xAxis : {
					categories : [ '2013-01', '2013-02', '2013-03', '2013-04', '2013-05', '2013-06', '2013-07', '2013-08', '2013-09', '2013-10', '2013-11', '2013-12']
				},
				
				tooltip : {
					formatter : function() {
						return '<b>' + this.series.name + '</b><br/>'
								+ Highcharts.dateFormat('%e. %b', this.x)
								+ ': ' + this.y;
					}
				},

				series : [ {
					name : '新增用户',
					data : [2,2,3,2,1,2,1,2,1,1,2]
				} ]
			});
		});
