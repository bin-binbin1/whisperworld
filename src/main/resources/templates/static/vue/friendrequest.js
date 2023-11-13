new Vue({
    el: '#friend',
    data: {
        friendRequests: [],// 接收后端发来的加好友请求
    },
    mounted() {
        // 在组件挂载后，发送请求获取加好友请求数据
        this.getFriendRequests();
    },
    methods: {
        getFriendRequests() {
            // 使用 Axios 或其他方式发送请求获取加好友请求数据
            axios.get('/api/getFriendRequests')
                .then(response => {
                    // 获取到加好友请求数据后更新 friendRequests
                    this.friendRequests = response.data;
                })
                .catch(error => {
                    console.error('Error getting friend requests:', error);
                });
        },
        handleDecision(senderUsername, decision) {
            // 发送请求给后端，处理添加/拒绝好友请求的逻辑
            axios.post('/api/handleFriendRequest', { senderUsername, decision })
                .then(response => {
                    console.log('Handle friend request:', senderUsername, decision);
                    // 根据后端响应来更新前端数据
                    if (response.data) {
                        // 示例：在前端删除已处理的好友请求
                        this.friendRequests = this.friendRequests.filter(request => request.senderUsername !== senderUsername);
                    } else {
                        console.error('Failed to handle friend request:', response.data.error);
                    }
                })
                .catch(error => {
                    console.error('Error handling friend request:', error);
                });
        }
    },
});