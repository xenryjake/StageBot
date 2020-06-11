package com.xenry.stagebot.audio.musicquiz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/23/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
@Deprecated
public final class TestSongListGenerator {
	
	private TestSongListGenerator(){}
	
	@Deprecated
	public static List<MusicQuizSong> generateA(){
		List<MusicQuizSong> songs = new ArrayList<>();
		songs.add(new MusicQuizSong(1, "https://www.youtube.com/watch?v=rp31_j9knMI", "Higher Ground", "Martin Garrix ft. John Martin", 50000, "Higher Ground", Arrays.asList("Martin Garrix", "John Martin")));
		songs.add(new MusicQuizSong(2, "https://www.youtube.com/watch?v=ejBVmFXXqB4", "Someone You Loved (Martin Garrix Remix)", "Lewis Capaldi", 10000,"Someone You Loved", Arrays.asList("Martin Garrix", "Lewis Capaldi")));
		return songs;
	}
	
}
