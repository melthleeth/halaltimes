const DJANGO_URL = process.env.VUE_APP_DJANGO_URL;
export default {
  // 장고와 연결
  async connectDjano() {
    const response = await fetch(
      `${DJANGO_URL}/recommendations/`,
      {
        method: 'GET'
      }
    );
    const responseData = await response.text();
    if (responseData === 'fail') {
      return 'FAIL'
    } else {
      return 'SUCCESS'
    }
  },

}