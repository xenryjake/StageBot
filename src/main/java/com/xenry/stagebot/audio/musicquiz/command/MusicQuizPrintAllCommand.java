package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;
import com.xenry.stagebot.audio.musicquiz.MusicQuizSong;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 6/1/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizPrintAllCommand extends AbstractMusicQuizCommand {
	
	public MusicQuizPrintAllCommand(MusicQuizHandler musicQuizHandler){
		super(musicQuizHandler, "printall");
		botAdminOnly = true;
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		List<MusicQuizSong> songs = musicQuizHandler.getAllSongs();
		songs = songs.subList(10, songs.size() - 1);
		int i = 0;
		
		/*if(args.length > 1){
			try{
				i = Integer.parseInt(args[0]);
			}catch(Exception ex){
				MessageUtil.sendMessage(message.getChannel(), ":x: Invalid integer.");
				return;
			}
		}*/
		
		StringBuilder sb = new StringBuilder();
		for(MusicQuizSong song : songs){
			sb.append("id: `").append(song.getSongID()).append("`\n");
			sb.append("  Title: `").append(song.getTitle()).append("`\n");
			sb.append("  Artist: `").append(song.getArtist()).append("`\n");
			sb.append("  Valid Titles: ");
			for(String valid : song.getValidTitles()){
				sb.append("`").append(valid).append("`,");
			}
			sb.append("\n").append("  Valid Artists: ");
			for(String valid : song.getValidArtists()){
				sb.append("`").append(valid).append("`,");
			}
			sb.append("\n\n");
			
			if(++i > 10){
				break;
			}
		}
		
		MessageUtil.sendEmbed(message.getChannel(), new EmbedBuilder().setTitle("printall").setDescription(sb.toString()).build());
		
	}
	
}
