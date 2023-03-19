import matplotlib.pyplot as plt
import numpy as np

labels = ["2", "4", "6", "10", "16", "100", "2000"]
ao_buff_tasks = [11691, 22114, 30638, 47328, 78021, 58413, 4089]
locks_buff_tasks = [10748, 21950, 30584, 46052, 68804, 69003, 71088]
ao_time = [300.031, 300.023, 300.01, 299.97, 296.485, 47.808, 2.521]
ao_own_tasks = [11724, 22175, 31964, 48206, 69848, 70353, 72727]
ao_all_task = []
locks_time = [298.609, 299.215, 285.276, 284.828, 298.909, 47.938, 2.513]

ao_6clients_1 = [6727, 5283, 32148, 5342]
locks_6clients_1 = [6590, 6105, 32171, 5632]

ao_6clients_bigbuff = np.array([399793, 33909, 204701, 32615])
ao_6clients_bigbuff //= 1000
locks_6clients_bigbuff = np.array([418739, 36111, 195676, 35968])
locks_6clients_bigbuff //= 1000

ao_6clients_ALL = np.array([2774692, 2413295, 409706, 231609])
ao_6clients_ALL //= 1000
locks_6clients_ALL = np.array([837478, 72222, 391352, 71936])
locks_6clients_ALL //= 1000

ao_6clients_2 = [32919, 192140, 32852]
locks_6clients_2 = [36225, 175988, 35746]

labelsobc = ["buff 10\nKlient 10", "buff 100\nKlient 10",
             "buff 10\nKlient 100", "buff 100\nKlient 100"]
for i in range(len(ao_buff_tasks)):
    ao_buff_tasks[i] /= 300000
    ao_own_tasks[i] /= 300000
    ao_all_task.append(ao_own_tasks[i]+ao_buff_tasks[i])
    locks_buff_tasks[i] /= 300000

x = np.arange(len(labels))  # the label locations
width = 0.35  # the width of the bars
# fig, ax = plt.subplots()
# rects1 = ax.bar(x - width/2, ao_buff_tasks, width, label='AO')
# rects2 = ax.bar(x + width/2, locks_buff_tasks, width, label='3 Locks')
# ax.set_ylabel('Średnia ilość wykonanych zadań na 1 sek.')
# ax.set_xlabel('Liczba wątków')
# ax.set_title('Porównanie średniej ilości modyfikacji bufora na 1 sek.')
# ax.set_xticks(x, labels)
# ax.legend()
# fig.tight_layout()
# plt.show()

# fig, ax = plt.subplots()
# rects1 = ax.bar(x - width/2, ao_all_task, width, label='AO')
# rects2 = ax.bar(x + width/2, locks_buff_tasks, width, label='3 Locks')
# ax.set_ylabel('Średnia ilość wykonanych zadań na 1 sek.')
# ax.set_xlabel('Liczba wątków')
# ax.set_title('Porównanie średniej ilości zadań wykonanych na 1 sek.')
# ax.set_xticks(x, labels)
# ax.legend()
# fig.tight_layout()
# plt.show()

# fig, ax = plt.subplots()
# rects1 = ax.bar(x - width/2, ao_time, width, label='AO')
# rects2 = ax.bar(x + width/2, locks_time, width, label='3 Locks')
# ax.set_ylabel('Średnia czas dostępu do procesora')
# ax.set_xlabel('Liczba wątków')
# ax.set_title('Porównanie ilości czasu dostępu do procesora')
# ax.set_xticks(x, labels)
# ax.legend()
# fig.tight_layout()
# plt.show()

x_obc = np.arange(len(labelsobc))
fig, ax = plt.subplots()
rects1 = ax.bar(x_obc - width/2, ao_6clients_ALL, width, label='AO')
rects2 = ax.bar(x_obc + width/2, locks_6clients_ALL,
                width, label='3 Locks')
ax.set_ylabel('Liczba wykonanych operacji (tys.)')
ax.set_xlabel('Liczba liczonych sinusów (tys.)')
ax.set_title('Ilość operacji w zależności od obciążenia')
ax.set_xticks(x_obc, labelsobc)
ax.legend()
fig.tight_layout()
plt.show()
