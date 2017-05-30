DELETE FROM VOTE_HISTORY;
DELETE FROM LUNCHES;
DELETE FROM RESTAURANTS;
DELETE FROM USER_ROLES;
DELETE FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 1;
ALTER SEQUENCE vote_history_seq RESTART WITH 1;

INSERT INTO USERS (NAME, EMAIL, PASSWORD, REGISTERED) VALUES
  ('Admin', 'admin@gmail.com', 'admin', '2015-01-01 00:00:00'),
  ('Maria', 'maria@yandex.ru', 'password', '2015-04-23 17:15:02'),
  ('Oleg', 'oleg@yandex.ru', 'psswd', '2015-05-15 14:15:00');

INSERT INTO USER_ROLES (role, user_id) VALUES
  ('ROLE_ADMIN', 1),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3);

INSERT INTO RESTAURANTS (NAME, STREET, BUILDING, ROUTE_GUIDE, WORK_FROM, WORK_UNTIL) VALUES
  ('Макдоналдс', 'МКАД, 47-й километр', '20', 'ТЦ Наш', '9:00:00', '22:30:00'),
  ('Крошка Картошка', 'МКАД, 47-й километр', '20', 'ТЦ Наш', '9:00:00', '22:00:00'),
  ('Шоколадница', 'Солнцевский проспект', '21', 'ТЦ Столица', '9:00:00', '22:00:00'),
  ('Бенджамин', '50 лет Октября', '2', '', '9:00:00', '0:00:00'),
  ('Subway', '50 лет Октября', '10', '', '9:00:00', '22:00:00');

INSERT INTO LUNCHES (NAME, PRICE, RESTAURANT_ID, DESCRIPTION) VALUES
  ('Гриль Гурмэ', 150, 4,
   'Рубленая говядина с добавлением свинины, обжаренная на гриле. Острый перец. Маринованые огурцы. Хрустящая булочка'),
  ('Чизбургер Фреш', 120, 4,
   'Сочная котлета из 100% говядины, сыр со вкусом бекона и перца. Ломтик свежего помидора салат и острый соус. Макчикен на регулярной булочке'),
  ('Картошка с копченостями', 125, 5, 'Энергерическая ценность на 100г: 116,16 Ккал'),
  ('Окрошка на квасе', 170, 5, 'Энергерическая ценность на 100г: 48 Ккал'),
  ('Эспрессо 30мл', 114, 6, 'Подается с бокалом воды'),
  ('Капучиино "Карамель" 32мл', 269, 6, 'С карамельным топпингом и ванильным сиропом'),
  ('Первое', 100, 7, 'Борщ со сметаной. Подается с чесночными гренками'),
  ('Второе', 170, 7, 'Куриная отбивня в кляре. Картофельное пюре'),
  ('Альпийский Саб', 150, 8,
   'Сочные колбаски с пряностями. Листья свежего салата с ломтиками помидоров и огурцов. Свежеиспеченый хлеб'),
  ('Сабвэй Клаб', 120, 8,
   'Соблазнительное сочетание ростбифа, ветчины и индейки, свежие овощи по Вашему выбору на свежевыпеченном хлебе');

INSERT INTO VOTE_HISTORY (RESTAURANT_ID, USER_ID, DATE) VALUES
  (4, 2, '2015-04-23'),
  (6, 2, '2015-04-24'),
  (8, 2, '2015-04-25'),
  (4, 2, '2015-04-26'),
  (7, 3, '2015-05-15'),
  (7, 3, '2015-05-16'),
  (7, 3, '2015-05-17'),
  (5, 3, '2015-05-18');


