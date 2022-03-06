import Vue from 'vue';

const errorNotification = (_error: any): void => {
  const errorMessage = _error;
  Vue.prototype.$notification.open({
    message: errorMessage,
    placement: 'bottomRight',
  });
};

const successNotification = (message: string): void => {
  Vue.prototype.$notification.open({
    message,
    placement: 'bottomRight',
  });
};
export { errorNotification, successNotification };
