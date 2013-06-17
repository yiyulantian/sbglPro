var chart1;
$(document).ready(function() {
	chart1 = new Highcharts.Chart( {
		chart : {
			renderTo : 'clienttypechart',
			type : 'bar'
		},
		title : {
			text : ''
		},
		// subtitle : {
		// text : '柱状显示2'
		// },
		xAxis : {
			categories : [ '公交公司', '货运公司', '物流公司', '个人公司' ],
			title : {
				text : null
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : '(人)',
				align : 'high'
			},
			labels : {
				overflow : 'justify'
			}
		},
		tooltip : {
			formatter : function() {
				return '' + this.series.name + ': ' + this.y + ' 人';
			}
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -100,
			y : 100,
			floating : true,
			borderWidth : 1,
			backgroundColor : '#FFFFFF',
			shadow : true
		},
		credits : {
			enabled : false
		},
		series : [ {
			name : '用户数',
			data : [ 3, 5, 6, 5 ]
		} ]
	});
});
