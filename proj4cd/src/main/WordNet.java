package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {

	private DirectedGraph directedGraph;
	private Map<Integer, String> idToSynset;
	private Map<String, Set<Integer>> wordToSynsetIds;

	public WordNet(String synsetsFileName, String hyponymsFileName) {
		this.directedGraph = new DirectedGraph();
		this.idToSynset = new HashMap<>();
		this.wordToSynsetIds = new HashMap<>();

		try (BufferedReader readerSynset = new BufferedReader(new FileReader(synsetsFileName));
		     BufferedReader readerHyponyms = new BufferedReader(new FileReader(hyponymsFileName)))
		{
			String line;

			while((line = readerSynset.readLine()) != null) {
				String[] parts = line.split(",");

				int id = Integer.parseInt(parts[0]);
				String synset = parts[1];
				// 装载B.Map<Integer, String> idToSynset
				idToSynset.put(id, synset);
				// 装载A.Map<String, Set<Integer>> wordToSynsetIds
				String[] syn = synset.split(" ");
				for (String s : syn) {
					wordToSynsetIds
							.computeIfAbsent(s, k -> new HashSet<>())
							.add(id);
				}
			}

			while((line = readerHyponyms.readLine()) != null) {
				String[] parts = line.split(",");
				int top = Integer.parseInt(parts[0]);
				for (int index = 1, length = parts.length; index < length; index++) {
					int part = Integer.parseInt(parts[index]);
					directedGraph.addEdge(top, part);
				}
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private Set<Integer> getIdBy(String word) {
		return wordToSynsetIds.get(word);
	}

	private Set<String> getSynById(Integer id) {
		String syn = idToSynset.get(id);
		return Set.of(syn.split(" "));
	}
	//多个单词
	public List<String> getHyponymsByWords(List<String> words) {
		if (words.isEmpty()) {
			return List.of();
		}
		Set<String> resultSet = new HashSet<>();
		for (String word : words) {
			Set<String> hyponyms = getHyponymsByWord(word);
			if (resultSet.isEmpty()) {
				resultSet = new HashSet<>(hyponyms);
			}
			resultSet.retainAll(hyponyms);
		}
		List<String> list = new ArrayList<>(resultSet);
		Collections.sort(list);
		return list;
	}

	private Set<String> getHyponymsByWord(String word) {
		Set<Integer> ids = getIdBy(word);
		if (ids == null) {
			return Collections.emptySet();
		}
		Set<Integer> tempSet = new HashSet<>();
		for (int id : ids) {
			tempSet.addAll(directedGraph.searchDFS(id));
		}
		Set<String> resultSet = new HashSet<>();
		for (int id : tempSet) {
			resultSet.addAll(getSynById(id));
		}
		return resultSet;
	}
}
