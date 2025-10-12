import heapq
import random
import time

## ---Adjacency Matrix + Array---
def create_adj_matrix(V, E):
    adj_matrix = [[float("inf")] * V for _ in range(V)]
    maxEdges = V*(V-1)
    edges = min(maxEdges, E)
    i=0
    while i<edges:
        u1 = random.randint(0,V-1)
        u2 = random.randint(0,V-1)
        if u1 != u2 and adj_matrix[u1][u2] == float("inf"):
            edgeweight = random.randint(1,20)
            adj_matrix[u1][u2] = edgeweight
            i+=1
    return adj_matrix

def dijkstra_matrix(graph, src):
    V = len(graph)
    dist = [float("inf")] * V
    visited = [False] * V
    dist[src] = 0
    comparisons = 0

    for _ in range(V):
        u = -1
        for i in range(V):
            if not visited[i] and (u == -1 or dist[i] < dist[u]):
                u = i
            comparisons += 1

        visited[u] = True
        for v in range(V):
            if graph[u][v] != float("inf") and not visited[v]:
                comparisons += 1
                if dist[u] + graph[u][v] < dist[v]:
                    dist[v] = dist[u] + graph[u][v]
    return comparisons

## ---Adjacency List + Min-Heap---
def create_adj_list(vertices, edges):
    graph = {i: [] for i in range(vertices)}
    max_edges = vertices * (vertices - 1)
    edges = min(edges, max_edges)
    added_edges = set()

    while len(added_edges) < edges:
        u = random.randint(0, vertices - 1)
        v = random.randint(0, vertices - 1)
        if u != v and (u, v) not in added_edges:
            weight = random.randint(1, 20)
            graph[u].append((v,weight))
            added_edges.add((u, v))
    return graph

def dijkstra_heap(graph, src):
    vertices = len(graph)
    dist = [float('inf')] * vertices
    dist[src] = 0
    visited = [False] * vertices
    comparisons = 0

    heap = [(0, src)]  # (distance, vertex)

    while heap:
        current_dist, u = heapq.heappop(heap)
        if visited[u]:
            continue
        visited[u] = True

        for v, weight in graph[u]:
            comparisons += 1
            if not visited[v] and current_dist + weight < dist[v]:
                dist[v] = current_dist + weight
                heapq.heappush(heap, (dist[v], v))
    return comparisons

## ---Empirical test---
def test_performance_matrix():
    print("V,E,Comparisons,Time_ns")
    for V in range(100, 1001, 100):
        E = min(20000, V * (V - 1))
        graph = create_adj_matrix(V, E)
        start = time.time_ns()
        comparisons = dijkstra_matrix(graph, 0)
        end = time.time_ns()
        print(f"{V},{E},{comparisons},{end - start}")

def test_performance_heap():
    print("V,E,Comparisons,Time_ns")
    for V in range(100, 1001, 100):
        E = min(20000, V * (V - 1))
        graph = create_adj_list(V, E)
        start = time.time_ns()
        comparisons = dijkstra_heap(graph, 0)
        end = time.time_ns()
        print(f"{V},{E},{comparisons},{end - start}")