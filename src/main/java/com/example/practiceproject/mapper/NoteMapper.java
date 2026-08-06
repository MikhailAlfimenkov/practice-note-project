package com.example.practiceproject.mapper;
import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {

    Note toEntity(CreationNoteRequest request);

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "authorSurname", source = "author.surname")
    NoteResponse toResponse(Note note);

    List<NoteResponse> toResponseList(List<Note> notes);

}
