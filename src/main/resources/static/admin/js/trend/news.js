window.addEventListener("DOMContentLoaded",function(){
  const ctx = document.getElementById('myChart');

  let data = document,getElementById("chart-data").innerHTML;
  const labels = Object.keys(data);
  const values = Object.values(data);

    new Chart(ctx, {
        type: 'pie',
        data: {
            labels,
            datasets: [{
                label: '핫 트렌드',
                data: values,
                borderWidth: 1
            }
        },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
}
        }
    });
})