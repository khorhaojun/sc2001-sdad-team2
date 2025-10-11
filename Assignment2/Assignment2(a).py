import random
import time

def createGraph(V, E):
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

# Empirical test
def test_performance():
    print("V,E,Comparisons,Time_ns")
    for V in range(1, 1001, 100):
        E = min(20000, V * (V - 1))
        graph = createGraph(V, E)
        start = time.time_ns()
        comparisons = dijkstra_matrix(graph, 0)
        end = time.time_ns()
        print(f"{V},{E},{comparisons},{end - start}")

test_performance()