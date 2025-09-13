import random
import time

class Counter:
    def __init__(self):
        self.value = 0
    def count(self):
        self.value += 1

def merge(list1, list2, counter: Counter):
    sorted_list = []
    i, j = 0, 0
    if list1 == [] or list2 == []:
        return list1 + list2
    while i < len(list1) and j < len(list2):
        counter.count()
        if list1[i] <= list2[j]:
            sorted_list.append(list1[i])
            i += 1
        else:
            sorted_list.append(list2[j])
            j += 1
    sorted_list += list1[i:] + list2[j:]
    return sorted_list
## I changed the method from using list[1:] to using index pointers cos the first time complexity is O(n^2) which is too slow

def merge_sort(arr, counter: Counter):
    if len(arr) <= 1:
        return arr
    mid = len(arr) // 2
    left_half = merge_sort(arr[:mid], counter)
    right_half = merge_sort(arr[mid:], counter)
    return merge(left_half, right_half, counter)

def insertion_sort(arr, counter: Counter):
    for i in range(1, len(arr)):
        for j in range(i-1, -1, -1):
            counter.count()
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]
            else:
                break
    return arr

def merge_insertion_sort(arr, S, counter: Counter):
    if len(arr) <= S:
        return insertion_sort(arr, counter)
    else:
        mid = len(arr) // 2
        left_half = merge_insertion_sort(arr[:mid], S, counter)
        right_half = merge_insertion_sort(arr[mid:], S, counter)
        return merge(left_half, right_half, counter)

def test(n, S):
    arr = [random.randint(1, n) for _ in range(n)]
    counter = Counter()
    start_time = time.time()
    sorted_arr = merge_insertion_sort(arr, S, counter)
    end_time = time.time()
    # print(sorted_arr)
    # print()
    return counter.value, end_time - start_time

if __name__ == "__main__":

    #S fixed, vary n
    print("=== Varying n with fixed S ===")
    S = int(input("Enter threshold S:"))
    sizes = [10**3, 10**4, 10**5, 10**6, 10**7]
    for n in sizes:
        comparisons, t = test(n, S)
        print(f"Array Size: {n}, Threshold: {S}, Comparisons: {comparisons}, Time: {t:.4f} seconds")

    #n fixed, vary S
    print("=== Varying S with fixed n ===")
    n = int(input("Enter array size n:"))
    #thresholds = [4, 8, 16, 32, 64, 128, 256]
        ##Not sure whether to use these specific thresholds or just all integers from 4 to 256
    for S in range(4, 257):
        comparisons, t = test(n, S)
        print(f"Array Size: {n}, Threshold: {S}, Comparisons: {comparisons}, Time: {t:.4f} seconds")