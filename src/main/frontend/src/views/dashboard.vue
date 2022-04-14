<template>
  <div class="dashboard">
    <a-card title="Поступления за последние 7 дней" class="income-graph">
      <apexchart type="area" height="350" :options="incomeOptionsGraph" :series="series1" />
    </a-card>
    <a-card title="Расходы за последние 7 дней" class="spend-graph mt-5">
      <apexchart type="area" height="350" :options="spendOptionsGraph" :series="series2" />
    </a-card>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import VueApexCharts from 'vue-apexcharts';

@Component({
  components: {
    apexchart: VueApexCharts,
  },
})
export default class Dashboards extends Vue {
  series1 = [
    {
      name: 'Поступления',
      data: [14500, 11484, 2482, 9774, 5487, 35413, 25475],
    },
  ];

  series2 = [
    {
      name: 'Расходы',
      data: [12479, 14774, 25874, 1452, 587, 2365, 6741],
    },
  ];

  chartOptions = {
    chart: {
      height: 350,
      type: 'area',
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: 'smooth',
    },
    xaxis: {
      type: 'date',
      categories: ['2022-04-05', '2022-04-06', '2022-04-07', '2022-04-08', '2022-04-09', '2022-04-10', '2022-04-11'],
    },
    tooltip: {
      x: {
        format: 'dd/MM/yy HH:mm',
      },
    },
  };

  get spendOptionsGraph(): any {
    return { ...this.chartOptions, title: { text: '' }, colors: ['red'] };
  }

  get incomeOptionsGraph(): any {
    return { ...this.chartOptions, title: { text: '' }, colors: ['green'] };
  }
}
</script>

<style scoped>
</style>
