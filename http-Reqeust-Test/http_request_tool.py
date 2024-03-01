import requests
import random
from concurrent.futures import ThreadPoolExecutor

def send_http_request(url, request_body=None, headers=None):
    try:
        response = requests.post(url, json=request_body, headers=headers)
        if response.status_code == 200:
            print(f"주문 생성 성공, 주문 정보 : {response.json()}")
        else:
            print(f"재고 없음, 주문 정보 : {response.json()}")
    except Exception as e:
        print(f"Error sending request to {url}: {e}")

def main():
    # Set the number of concurrent requests (N)
    num_requests = 50

    # Set the target URL
    target_url = "http://172.29.224.1:8085/api/v1/orders"

    # Create a ThreadPoolExecutor to send concurrent requests
    with ThreadPoolExecutor(max_workers=num_requests) as executor:
        # Use a list comprehension to create a list of tasks
        tasks = []
        for _ in range(num_requests):
            # Generate random user_id
            user_id = random.randint(1, 100)
            # Define request body and headers
            request_body = {
                "productId" : 13,
                "quantity" : 1,
                "address" : "서울시"
            }
            headers = {
                "principalId": str(user_id),
                "Content-Type": "application/json"
            }
            # Submit task to ThreadPoolExecutor
            task = executor.submit(send_http_request, target_url, request_body, headers)
            tasks.append(task)

        # Wait for all tasks to complete
        for future in tasks:
            future.result()

if __name__ == "__main__":
    main()
