window.addEventListener("DOMContentLoaded",function(){

    let data = document.getElementById("chart-data").innerHTML;
    data = JSON.parse(data);
    const labels = Object.keys(data);
    const values = Object.values(data);

    const ctx = document.getElementById('myChart');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels,
            datasets: [{
                label: '핫 트렌드',
                data: values,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});