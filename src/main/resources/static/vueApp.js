const { createApp, ref, onMounted } = Vue;

const apiUrl = 'http://ec2-44-213-103-70.compute-1.amazonaws.com:8080/todo';

createApp({
    setup() {
        const todos = ref([]);
        const currentPage = ref(0);
        const totalPages = ref(0);

        const loadTodos = (page) => {
            axios.get(`${apiUrl}?page=${page}&size=10&sort=id`)
                .then(response => {
                    todos.value = response.data.content.map(todo => ({
                        id: todo.id,
                        descricao: todo.descricao,
                        data: todo.data,
                        concluido: todo.concluido
                    }));
                    currentPage.value = response.data.number;
                    totalPages.value = response.data.totalPages;
                })
                .catch(error => {
                    console.error('Error loading todos:', error);
                });
        };

        const toggleStatus = (todo) => {
            const url = `${apiUrl}/${todo.id}/${todo.concluido ? 'undo' : 'done'}`;
            axios.put(url, { concluido: !todo.concluido })
                .then(() => {
                    loadTodos(currentPage.value);
                })
                .catch(error => {
                    console.error('Error toggling todo status:', error);
                });
        };
        
        const deleteTodo = (id) => {
            if (confirm(`Deseja realmente deletar o ToDo ${id}?`)) {
                axios.delete(`${apiUrl}/${id}`)
                    .then(() => {
                        loadTodos(currentPage.value);
                    })
                    .catch(error => {
                        console.error('Error deleting todo:', error);
                    });
            }
        };

        const getPaginationRange = () => {
            const range = [];
            let start = Math.max(currentPage.value - 2, 0);
            let end = Math.min(start + 4, totalPages.value - 1);

            if (end - start < 4) {
                start = Math.max(end - 4, 0);
            }

            for (let i = start; i <= end; i++) {
                range.push(i);
            }

            if (end < totalPages.value - 1) {
                range.push(end + 1);
            }

            if (range.length > 5) {
                range.shift();
            }

            return range;
        };

        const loadPage = (page) => {
            loadTodos(page);
        };

        const redirectToFormTodo = () => {
            window.location.href = 'add-todo.html';
        };

        onMounted(() => {
            loadTodos(0);
        });

        return {
            todos,
            currentPage,
            totalPages,
            toggleStatus,
            deleteTodo,
            loadPage,
            getPaginationRange,
            redirectToFormTodo
        };
    }
}).mount('#app');
