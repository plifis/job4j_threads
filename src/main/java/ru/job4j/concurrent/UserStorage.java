package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> map = new ConcurrentHashMap<>();


    /**
     * Создаем объект User по значениям полученным из хранилища и возращаем его
     * @param id идентификатор (ключ) объекта User
     * @return созданный объект User, если идентфиикатор (ключ) не найден возращаем null
     */
    public synchronized User getUser(int id) {
        User temp = map.get(id);
        return new User(temp.getId(), temp.getAmount());
    }

    /**
     * Перевод со счета одного объекта User на счет другого объекта User
     * @param fromId идентификатор (ключ) с которого необходимо перевести сумму
     *               (переводится со счета объекта User)
     * @param toId идентификатор (ключ) на который необходимо перевести сумму
     *             (переводится на счет объекта User)
     * @param amount сумма перевода
     */
    public synchronized void transfer(int fromId, int toId, int amount) {
        User fromUser = map.get(fromId);
        User toUser = map.get(toId);
        if (this.checkAmount(fromUser, amount) && (map.containsKey(toId))) {
            this.decrease(fromUser, amount);
            this.increase(toUser, amount);
        }
    }

    /**
     * Увеличивание значения поля amount у объекта User
     * @param user объект User у которого необходимо увеличить счет
     * @param amount сумма увеличения
     */
    private synchronized void increase(User user, int amount) {
        this.update(new User(user.getId(), user.getAmount() + amount));
    }

    /**
     * Уменьшение значения поля amount у объекта User
     * @param user объект у которого необходимо уменьшить счет
     * @param amount сумма уменьшения
     */
    private synchronized void decrease(User user, int amount) {
        this.update(new User(user.getId(), user.getAmount() - amount));
    }

    /**
     * Проверка остатка на счету у объекта User и возможности списания со счета требуемой суммы
     * @param user объект User у которого необходимо проверить возможность списания суммы
     * @param amount сумма для списания
     * @return возвращаем истина, если списание возможно
     */
    private synchronized boolean checkAmount(User user, int amount) {
        return user.getAmount() <= amount;
    }

    /**
     * Обновление объекта User
     * @param user Объект, который необходимо обновить
     * @return возращаем истина, если метод замены в хранилище вернул значение отличное от null
     */
    public synchronized boolean update(User user) {
        return map.replace(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    /**
     * Добавление объекта в хранилище
     * @param user Объект, который необходимо добавить
     * @return возращаем истина, если метод добавления в хранилище вернул значение отличное от null
     */

    public synchronized boolean add(User user) {
      return map.put(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    /**
     * Удаление объекта User из хранилища
     * @param user Объект, который необходимо удалить
     * @return возвращаем истина, если объект удален
     */
    public synchronized boolean delete(User user) {
       return map.remove(user.getId(), new User(user.getId(), user.getAmount()));
    }
}
