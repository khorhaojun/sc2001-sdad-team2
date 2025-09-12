def merge(list1, list2):
    sorted_list = []
    if list1 == [] or list2 == []:
        return list1 + list2
    while list1 != [] and list2 != []:
        head1, head2 = list1[0], list2[0]
        if head1 < head2:
            sorted_list.append(head1)
            list1 = list1[1:]
        else:
            sorted_list.append(head2)
            list2 = list2[1:]
    return sorted_list + list1 + list2

# def merge_sort(arr):
#     if len(arr) <= 1:
#         return arr
#     mid = len(arr) // 2
#     left_half = merge_sort(arr[:mid])
#     right_half = merge_sort(arr[mid:])
#     return merge(left_half, right_half)

def insertion_sort(arr):
    for i in range(1, len(arr)):
        for j in range(i-1, -1, -1):
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]
            else:
                break
    return arr

def merge_insertion_sort(arr, S):
    if len(arr) <= S:
        return insertion_sort(arr)
    else:
        mid = len(arr) // 2
        left_half = merge_insertion_sort(arr[:mid], S)
        right_half = merge_insertion_sort(arr[mid:], S)
        return merge(left_half, right_half)


# numbers = list(map(int, input().split()))
# threshold = int(input())
# print(merge_insertion_sort(A, S))