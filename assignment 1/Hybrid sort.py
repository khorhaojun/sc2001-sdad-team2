import random
import time
arrays = {}
arrays[1000] = [random.randint(1, 1000) for _ in range(1000)]
arrays[5000] = [random.randint(1, 1000) for _ in range(5000)]

def merge(A, B):
    i = j = 0
    sorted_list = []
    while i < len(A) and j < len(B):
        if A[i] >= B[j]:
            sorted_list.append(A[i])
            i += 1
        else:
            sorted_list.append(B[j])
            j += 1
    return sorted_list + A[i:] + B[j:]


def insertion_sort(A):
    n = len(A)
    for i in range(1,n):
        for j in range(i,0,-1):
            if A[j] < A[j-1]:
                A[j], A[j-1] = A[j-1],A[j]
            else:
                break
    return A

def hybrid_sort(A,S):
    if len(A) <= 1:
        return A
    if len(A) <= S:
        return insertion_sort(A)
    mid = len(A)//2
    lower = A[:mid]
    higher = A[mid:]
    A1 = hybrid_sort(lower,S)
    A2 = hybrid_sort(higher,S)
    return merge(A1,A2)

for i in arrays.values():
    hybrid_sort(i,5)
