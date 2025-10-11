import heapq
import random
import time

# Generate a random directed weighted graph as an adjacency list
def generate_graph(vertices, edges):
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

# Dijkstra's algorithm using adjacency list and min-heap
def dijkstra(graph, src):
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

# Empirical test
def test_performance():
    print("V,E,Comparisons,Time_ns")
    for V in range(100, 1001, 100):
        E = min(20000, V * (V - 1))
        graph = generate_graph(V, E)
        start = time.time_ns()
        comparisons = dijkstra(graph, 0)
        end = time.time_ns()
        print(f"{V},{E},{comparisons},{end - start}")

test_performance()