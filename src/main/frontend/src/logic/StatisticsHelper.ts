import { cloneDeep } from 'lodash';
import moment from 'moment';

const chartOptions = {
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
  tooltip: {
    x: {
      format: 'yy/MM/dd',
    },
  },
};

const spendOptionsGraph = (dateTicks: Array<string>): any => ({
  ...chartOptions,
  title: { text: '' },
  colors: ['red'],
  labels: dateTicks,
});

const incomeOptionsGraph = (dateTicks: Array<string>): any => ({
  ...chartOptions,
  title: { text: '' },
  colors: ['green'],
  labels: dateTicks,
});

export default function prepareStatistics(data: any): any {
  const dateTicks: Array<string> = [];
  const values: Array<number> = [];

  const from = moment().add(-7, 'd');
  const to = moment();

  while (from.isBefore(to)) {
    dateTicks.push(from.add(1, 'd').format('YYYY-MM-DD'));
    values.push(0);
  }

  console.log(incomeOptionsGraph(dateTicks));

  const resObj = {
    income: {
      series: [{ name: 'приход', data: cloneDeep(values) }],
      options: incomeOptionsGraph(dateTicks),
    },
    spend: {
      series: [{ name: 'Расход', data: cloneDeep(values) }],
      options: spendOptionsGraph(dateTicks),
    },
  };

  data.forEach((element: any) => {
    const idx = dateTicks.indexOf(element.date);
    if (idx > 0) {
      resObj.income.series[0].data[idx] = element.income_sum;
      resObj.spend.series[0].data[idx] = element.spend_sum;
    }
  });

  return resObj;
}
