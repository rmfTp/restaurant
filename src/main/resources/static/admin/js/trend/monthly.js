window.addEventListener("DOMContentLoaded",function(){

    let data = document.getElementById("chart-data").innerHTML;
    data = JSON.parse(data);
    const labels = Object.keys(data);
    const values = Object.values(data);

    const ctx = document.getElementById('dailyChart');
    const labels = Utils.da({count: 7});
    const data = {
      labels: labels,
      datasets: [
        {
          label: 'Dataset 1',
          data: Utils.numbers(NUMBER_CFG),
          borderColor: Utils.CHART_COLORS.red,
          backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
          yAxisID: 'y',
        },
        {
          label: 'Dataset 2',
          data: Utils.numbers(NUMBER_CFG),
          borderColor: Utils.CHART_COLORS.blue,
          backgroundColor: Utils.transparentize(Utils.CHART_COLORS.blue, 0.5),
          yAxisID: 'y1',
        }
      ]
    };
});