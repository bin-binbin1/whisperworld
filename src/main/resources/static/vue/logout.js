new Vue({
    el: '#out',
    methods: {
        logout: function() {
            // 发起 Axios 请求到后端来删除 session 信息
            axios.post('/logout')  // 请根据你的后端实际情况修改请求的URL
                .then(response => {
                    // 处理成功响应，例如重定向到登录页面
                    window.location.href = '/welcome';
                })
                .catch(error => {
                    // 处理错误，例如输出错误信息
                    console.error('Logout failed:', error);
                });
        }
    }
});