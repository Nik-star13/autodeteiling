window.loadNewOrders = async function() {
    try {
        const response = await fetch('/api/orders?status=NEW');
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        const orders = await response.json();
        displayOrders(orders);
    } catch(error) {
        document.getElementById('ordersTable').innerHTML =
            '<tr><td colspan="7" class="text-center text-danger p-4">Ошибка загрузки заказов</td></tr>';
    }
}

window.loadOrderById = async function() {
    const id = document.getElementById('orderIdInput').value.trim();
    if (!id) {
        alert('Введите ID заказа');
        return;
    }

    try {
        const response = await fetch(`/api/orders/${id}`);
        if (response.status === 404) {
            alert('Заказ не найден');
            return;
        }
        if (!response.ok) throw new Error(`HTTP ${response.status}`);

        const order = await response.json();
        if (!order || !order.id) throw new Error('Неверный формат заказа');
        displayOrders([order]);
    } catch(error) {
        alert('Ошибка поиска заказа');
    }
}

function displayOrders(orders) {
    const tbody = document.getElementById('ordersTable');
    if (!orders || orders.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted p-4">Заявок не найдено</td></tr>';
        return;
    }

    tbody.innerHTML = orders.map(order => {
        const servicesText = Array.isArray(order.services)
            ? order.services.join(', ')
            : (order.services || '—');
        const totalAmount = order.totalAmount || '0 BYN';
        const orderDate = order.date
            ? new Date(order.date).toLocaleString('ru-BY')
            : (order.orderDate ? new Date(order.orderDate).toLocaleString('ru-BY') : '—');

        return `
            <tr>
                <td><strong>${order.id || '—'}</strong></td>
                <td>${order.customerName || '—'}</td>
                <td>${order.phone || '—'}</td>
                <td>${order.carBrand || '—'}</td>
                <td>${servicesText}</td>
                <td><strong>${totalAmount}</strong></td>
                <td>${orderDate}</td>
            </tr>
        `;
    }).join('');
}
