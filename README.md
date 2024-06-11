# TestMall

---
## 1. 프로젝트 목표
### 1. 1차 목표
1. 사용자가 회원가입이 가능하고 로그인이 가능해야 한다.
2. 로셜로그인이 가능해야 한다.
3. 관리자가 상품을 등록할 수 있어야 한다.
4. 사용자가 상품을 구매할 수 있어야 한다.
5. 사용자가 상품을 장바구니에 담을 수 있어야 한다.
6. 사용자가 게시판을 통해 질문을 할 수 있어야 한다.

## 2. 2차 목표
1. 배치를 통해 상품 배송이 완료된 후 일정시간이 지나면 상품을 자동으로 구매확정 처리한다.

## 3. 3차 목표
1. aws를 통해 배포한다.

---
## 2. ERD
```mermaid
erDiagram
    MEMBER {
        Long id PK "member_id"
        String email
        String password
        String name
        String phone
        Role role
        Address address
        String socialLogin
    }
    ORDER {
        Long id PK "order_id"
        String paymentMethod
        String shippingMethod
        Date orderDate
        OrderStatus orderStatus
        Date regDate
        Date updateDate
        Long member_id FK
    }
    ORDERPRODUCT {
        Long id PK "order_product_id"
        int price
        int quantity
        Long order_id FK
        Long product_id FK
    }
    PRODUCT {
        Long id PK "product_id"
        String name
        int price
        int stockQuantity
        boolean isSoldOut
        String imageUrl
        String description
        Date regDate
        Date updateDate
    }
    DELIVERY {
        Long id PK "delivery_id"
        String receiverName
        String receiverPhone
        String receiverCity
        String receiverStreet
        String receiverZipcode
        Address address
        DeliveryStatus deliveryStatus
        Long order_id FK
    }
    CATEGORY {
        Long id PK "category_id"
        String name
        Long parent_id FK
    }
    CATEGORYPRODUCT {
        Long id PK "category_product_id"
        Long category_id FK
        Long product_id FK
    }
    CART {
        Long id PK "cart_id"
        Long member_id FK
    }
    CARTPRODUCT {
        Long id PK "cart_product_id"
        int quantity
        Long cart_id FK
        Long product_id FK
    }
    MEMBER ||--o{ ORDER : "places"
    ORDER ||--o{ ORDERPRODUCT : "contains"
    ORDERPRODUCT ||--|| PRODUCT : "represents"
    PRODUCT ||--o{ CATEGORYPRODUCT : "categorized_as"
    ORDER ||--|| DELIVERY : "delivers"
    CATEGORY ||--o{ CATEGORY : "sub_category"
    CATEGORYPRODUCT ||--|| CATEGORY : "categorized_as"
    CATEGORYPRODUCT ||--|| PRODUCT : "represents"
    CART ||--|| MEMBER : "belongs_to"
    CARTPRODUCT ||--|| CART : "belongs_to"
    CARTPRODUCT ||--|| PRODUCT : "contains"
```
---
## 3. API 명세서
### 1. 회원관련
| Method |      Path      |   Description    |
|:------:|:--------------:|:----------------:|
|  POST  |    /signup     |    회원가입을 한다.     |
|  POST  |     /login     |     로그인을 한다.     |
| PATCH  | /member/update |   회원정보를 수정한다.    |
|  GET   |  /memberlist   |   회원목록을 가져온다.    |
|  POST  |  /passwordCheck   |   비밀번호 유효성 검사.   |
|  GET   |  /kakaoLoginSuccess   | 카카오로 로그인 했을때 처리. |

### 2. 상품관련
| Method |     Path      |      Description      |
|:------:|:-------------:|:---------------------:|
|  POST  | /product/save |       상품을 등록한다.       |
|  GET   | /productlist  |      상품목록을 제공한다.      |
|  GET   | /product/{id} |   특정 상품의 정보를 제공한다.    |
|  POST  |   /category   |     카테고리를 등록합니다.      |
|  GET   |  /categories  |    카테고리 목록을 제공합니다.    |
|  POST  |  /cart/add    |   장바구니에 상품을 등록합니다.    |
|  GET   |  /cart/products/{email}   | 특정회원의 장바구니 목록을 제공합니다. |
| DELETE | /cart/delete/{productName}/{userEmail}  | 특정회원의 장바구니 상품을 삭제합니다. |

