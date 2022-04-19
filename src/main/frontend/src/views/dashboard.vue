<template>
  <a-spin :spinning="loading">
    <div v-if="statistics" class="dashboard">
      <a-card title="Поступления за последние 7 дней" class="income-graph">
        <apexchart type="area" height="350" :options="statistics.income.options" :series="statistics.income.series" />
      </a-card>
      <a-card title="Расходы за последние 7 дней" class="spend-graph mt-5">
        <apexchart type="area" height="350" :options="statistics.spend.options" :series="statistics.spend.series" />
      </a-card>
    </div>
  </a-spin>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import Vue from 'vue';
import VueApexCharts from 'vue-apexcharts';
import StatisticsService from '@/services/StatisticsService';
import prepareStatistics from '@/logic/StatisticsHelper';

@Component({
  components: {
    apexchart: VueApexCharts,
  },
})
export default class Dashboards extends Vue {
  statistics: any = null;

  loading = false;

  created(): void {
    this.loading = true;
    StatisticsService.getStatistics({ company_id: this.$store.state.companyModule.company.id })
      .then((res) => {
        this.statistics = prepareStatistics(res);
        this.loading = false;
      })
      .finally(() => {
        this.loading = false;
      });
  }
}
</script>

<style scoped>
</style>
